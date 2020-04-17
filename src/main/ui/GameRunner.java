package ui;

import model.Chamber;
import model.EventLogger;
import model.characters.Player;
import model.items.Item;
import persistence.ChamberReader;
import ui.hud.Icon;
import ui.hud.SelectableIcon;
import ui.hud.StatsDisplay;
import ui.sprites.AnimatedSprite;
import ui.sprites.PlayerSprite;
import ui.sprites.Sprite;
import ui.sprites.SpriteSheet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the runner for the game.
 */

public class GameRunner extends JFrame implements Runnable {
    private Canvas canvas = new Canvas();
    private Renderer renderer;

    private Map map;
    private PlayerSprite playerSprite;
    private List<UserInterfaceObject> userInterfaceObjects;

    private StatsDisplay statsDisplay;
    private int selectedTileID = 1;

    public static final int ALPHA = 0xFFFF00DC;
    public static final int ZOOMX = 3;
    public static final int ZOOMY = 3;

    // EFFECTS: creates a new game by initializing the Chamber and UI, and then
    //          continues to run until the game is over
    public GameRunner() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1300, 800);
        setLocationRelativeTo(null);
        add(canvas);
        setVisible(true);
        canvas.createBufferStrategy(3);

        MusicPlayer.getInstance().playMusicContinuously("assets/8bitMusic.wav");

        initializeGame();

        KeyEventListener keyEventListener = new KeyEventListener(this);
        canvas.addKeyListener(keyEventListener);

        MouseEventListener mouseEventListener = new MouseEventListener(this);
        canvas.addMouseListener(mouseEventListener);
        canvas.addMouseMotionListener(mouseEventListener);

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    // getters
    public Renderer getRenderer() {
        return renderer;
    }

    public int getSelectedTileID() {
        return selectedTileID;
    }

    public PlayerSprite getPlayerSprite() {
        return playerSprite;
    }

    public Map getMap() {
        return map;
    }

    // MODIFIES: this
    // EFFECTS: sets up the game by displaying introductory info, creating a new player, and a new chamber
    //          and then loading in UI elements
    private void initializeGame() {
        renderer = new Renderer(getWidth(), getHeight());
        String name = getUserInput("Welcome to Eterna\nWhat is your name brave adventurer?");
        showMessage("Use WASD to move and click on items in your inventory and hit \"E\" to use them.\n"
                + "At any time, you may use \"O\" to open options and either:\n"
                + "- save the current file\n"
                + "- load a previously saved file\n"
                + "Good luck!");
        Player player = new Player(name, 150, 20);
        Chamber chamber = new Chamber(player);
        initializeGame(chamber);
    }

    // MODIFIES: this
    // EFFECTS: creates the map for the game and loads in the player, along with their associated sprites
    private void initializeGame(Chamber chamber) {
        BufferedImage sheetImage = loadImage("assets/dungeonsheet.png");
        assert sheetImage != null;
        SpriteSheet sheet = new SpriteSheet(sheetImage, 16, 16);

        BufferedImage playerSheetImage = loadImage("assets/player.png");
        assert playerSheetImage != null;
        SpriteSheet playerSheet = new SpriteSheet(playerSheetImage, 20, 26);

        if (map == null) {
            map = new Map(sheet, chamber);
        } else {
            map.setChamber(chamber);
        }
        AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 5);
        userInterfaceObjects = new ArrayList<>();
        playerSprite = new PlayerSprite(playerAnimations, chamber.getPlayer());
        statsDisplay = new StatsDisplay(10, getHeight() - 80, true);
        userInterfaceObjects.add(map);
        userInterfaceObjects.add(playerSprite);
        userInterfaceObjects.add(statsDisplay);
    }

    // EFFECTS: displays a message with the given text to the user
    private void showMessage(String text) {
        JOptionPane.showMessageDialog(this,
                text,
                "Eterna",
                JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: displays a prompt to the user and returns their response
    private String getUserInput(String prompt) {
        return JOptionPane.showInputDialog(this,
                prompt,
                "Eterna",
                JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: renders and updates the game every frame according to what is happening in the Chamber
    //          while the player is alive, also handles displaying the grid, stats, and events at every turn
    //          allows user to make a move, then moves enemies
    public void run() {
        // makes sure things like player movement do not run faster on faster computers but
        // still takes advantage of higher power – update is same time, render is as often as possible
        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
        double changeInSeconds = 0;

        while (true) {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            while (changeInSeconds >= 1) {
                update();
                changeInSeconds--;
            }

            render();
            lastTime = now;
        }
    }

    // MODIFIES: this
    // EFFECTS: if the player is alive, updates all game objects
    //          else allows user to start new game or load in existing
    private void update() {
        if (map.getChamber().getPlayer().isAlive()) {
            userInterfaceObjects.forEach(gameObject -> gameObject.update(this));
        } else {
            String response = getUserInput("You died!\n"
                    + "Enter \"new\" to restart or \"load\" to load a previous save.");
            switch (response.toLowerCase()) {
                case "new":
                    EventLogger.addToLogs("Welcome Back to Eterna!");
                    initializeGame();
                    run();
                    break;
                case "load":
                    handleLoadGame();
                    break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays all UI elements according to the Chamber at the current moment
    private void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        renderer.clear();
        userInterfaceObjects.forEach(gameObject -> gameObject.render(renderer, ZOOMX, ZOOMY));
        renderer.render(graphics);

        List<Item> playerInventory = map.getChamber().getPlayer().getInventory();
        List<ui.hud.Icon> buttons = new ArrayList<>();

        for (int i = 0; i < playerInventory.size(); i++) {
            int posX = i * (16 * ZOOMX + 2);
            Rectangle tileRectangle = new Rectangle(posX, 0, 16 * ZOOMX, 16 * ZOOMY);
            Sprite sprite = map.getSpriteForEntity(playerInventory.get(i));
            Icon icon = new SelectableIcon(this, i + 1, sprite, tileRectangle);
            buttons.add(icon);
        }

        statsDisplay.getInventoryButtons().clear();
        statsDisplay.getInventoryButtons().addAll(buttons);
        statsDisplay.showPlayerStats(map.getChamber().getPlayer(), graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    // MODIFIES: this
    // EFFECTS: changes the selected tile to the given tileID
    public void changeTile(int tileID) {
        selectedTileID = tileID;
    }

    // EFFECTS: saves the current chamber to file and continues the game
    public void handleSaveGame() {
        try {
            map.getChamber().save();
            showMessage("Game has been saved successfully!");
        } catch (IOException e) {
            showMessage("Game could not be saved!");
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user to input file name and loads that file to currentChamber
    public void handleLoadGame() {
        StringBuilder filesString = new StringBuilder();
        File[] savedFiles = ChamberReader.getInstance().getAllFiles();
        if (savedFiles != null) {
            for (int i = 0; i < savedFiles.length; i++) {
                filesString.append("[").append(i).append("] ").append(savedFiles[i].getName()).append("\n");
            }
            String userInput = getUserInput("Which file would you like to load:\n" + filesString);
            try {
                userInput = savedFiles[Integer.parseInt(userInput)].getName();
                Chamber chamber = ChamberReader.getInstance().readChamber(userInput);
                System.out.println("Loaded file successfully!");
                initializeGame(chamber);
            } catch (IOException e) {
                System.out.println("Could not read file.");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("That file does not exist!");
            }
        } else {
            showMessage("No saved files!");
        }
    }

    // MODIFIES: this
    // EFFECTS: determines what should be done when the mouse is clicked on a UI element
    public void leftClick(int x, int y) {
        boolean stopped = false;
        Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
        for (UserInterfaceObject userInterfaceObject : userInterfaceObjects) {
            if (!stopped) {
                stopped = userInterfaceObject.handleMouseClick(mouseRectangle, renderer.getCamera(), ZOOMX, ZOOMY);
            }
        }
    }

    // EFFECTS: returns the image at the given path or null if the file cannot be read
    private static BufferedImage loadImage(String path) {
        try {
            BufferedImage loadedImage = ImageIO.read(new File(path));
            BufferedImage formattedImage = new BufferedImage(
                    loadedImage.getWidth(),
                    loadedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);

            return formattedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}