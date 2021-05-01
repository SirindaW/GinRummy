/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GinRummy extends Application {

    private Deck deck = new Deck();
    private Player player;
    private Bot bot;
    private UpCard upcard;
    private DrawPile drawpile;

    HBox playerCardsPane = new HBox(10);
    private Glow glow = new Glow();
    private boolean firstPass = false;
    private boolean botTurn = false;
    private boolean isTake = false;

    private Parent createGame() {
        System.out.println("creatgame laewja");
        //set main pane
        Pane root = new Pane();
        root.setPrefSize(1000, 600);

        Region background = new Region();
        background.setPrefSize(1000, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        //set next pane
        StackPane rootLayout = new StackPane();
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        Rectangle BG = new Rectangle(973, 550);
        BG.setArcWidth(30);
        BG.setArcHeight(30);
        BG.setFill(Color.GREEN);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        //set final pane        
        HBox botCardsPane = new HBox(10);
        HBox botDeadwoodCardsPane = new HBox(10);
        HBox botStraightCardsPane = new HBox(10);
        HBox botKindCardsPane = new HBox(10);

        HBox playerDeadwoodCardsPane = new HBox(10);
        HBox playerStraightCardsPane = new HBox(10);
        HBox playerKindCardsPane = new HBox(10);

        HBox drawCardsPane = new HBox(10);
        StackPane upCardPilePane = new StackPane();
        StackPane drawPilePane = new StackPane();
        playerDeadwoodCardsPane.setPadding(new Insets(5, 5, 5, 5));
        botDeadwoodCardsPane.setPadding(new Insets(5, 5, 5, 5));
        playerKindCardsPane.setPadding(new Insets(5, 5, 5, 5));
        botKindCardsPane.setPadding(new Insets(5, 5, 5, 5));
        playerStraightCardsPane.setPadding(new Insets(5, 5, 5, 5));
        botStraightCardsPane.setPadding(new Insets(5, 5, 5, 5));

        playerDeadwoodCardsPane.setAlignment(Pos.CENTER);
        botDeadwoodCardsPane.setAlignment(Pos.CENTER);
        playerKindCardsPane.setAlignment(Pos.CENTER);
        botKindCardsPane.setAlignment(Pos.CENTER);
        playerStraightCardsPane.setAlignment(Pos.CENTER);
        botStraightCardsPane.setAlignment(Pos.CENTER);
        upCardPilePane.setAlignment(Pos.CENTER);
        drawPilePane.setAlignment(Pos.CENTER);
        playerCardsPane.setAlignment(Pos.CENTER);
        botCardsPane.setAlignment(Pos.CENTER);

        drawCardsPane.setAlignment(Pos.CENTER);
        drawCardsPane.getChildren().addAll(drawPilePane, upCardPilePane);

        //declare arraylist
        player = new Player(playerDeadwoodCardsPane.getChildren(), playerStraightCardsPane.getChildren(), playerKindCardsPane.getChildren());
        bot = new Bot(botDeadwoodCardsPane.getChildren(), botStraightCardsPane.getChildren(), botKindCardsPane.getChildren());
        upcard = new UpCard(upCardPilePane.getChildren());
        drawpile = new DrawPile(drawPilePane.getChildren());
        playerCardsPane.getChildren().addAll(playerKindCardsPane, playerStraightCardsPane, playerDeadwoodCardsPane);
        botCardsPane.getChildren().addAll(botKindCardsPane, botStraightCardsPane, botDeadwoodCardsPane);

        //Bot Score
        Text botScore = new Text("Dealer Score : ");
        botScore.setFont(Font.font("Tahoma", 18));
        botScore.setFill(Color.WHITE);

        Text passText = new Text("PASS");
        passText.setFont(Font.font("Tahoma", 40));
        passText.setFill(Color.YELLOW);
        passText.setX(200);
        passText.setY(200);

        //Player Score
        HBox playerInfo = new HBox(30);
        Text playerScore = new Text("Player Score : ");
        Text playerDeadwood = new Text("Player Deadwood : ");
        playerScore.setFont(Font.font("Tahoma", 18));
        playerScore.setFill(Color.WHITE);

        playerDeadwood.setFont(Font.font("Tahoma", 18));
        playerDeadwood.setFill(Color.WHITE);

        playerInfo.setAlignment(Pos.CENTER);
        playerInfo.getChildren().addAll(playerScore, playerDeadwood);

        //All button
        HBox buttonBox = new HBox(5);
        buttonBox.setPadding(new Insets(5, 5, 5, 5));
        Button btnPass = new Button("PASS");
        Button btnTake = new Button("TAKE");
        Button btnNew = new Button("NEW");
        Button btnDiscard = new Button("DISCARD");
        Button btnKnock = new Button("KNOCK");

        // ADD STACKS TO ROOT LAYOUT
        buttonBox.getChildren().addAll(btnTake, btnPass);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        vbox.getChildren().addAll(botCardsPane, botScore, drawCardsPane, buttonBox, playerInfo, playerCardsPane);
        rootLayout.getChildren().addAll(new StackPane(BG), new StackPane(vbox));
        root.getChildren().addAll(background, rootLayout);

        // INIT BUTTONS
        btnTake.setOnAction(event -> {
            player.takeDeadwoodCard((Card) upcard.drawCard());
            if (firstPass == true) {
                root.getChildren().remove(passText);
            }

            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            //Score 
            playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
            playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
            botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Deadwood())));
        });

        btnPass.setOnAction(event -> {
            if (bot.botAction(upcard) == 0) {
                bot.takeDeadwoodCard((Card) upcard.drawCard());
                bot.sortDeadwoodCards();
                upcard.keepCard((Card) bot.botDropCard());
            } else if (bot.botAction(upcard) == 1) {
                bot.takeKindCard((Card) upcard.drawCard());
                bot.sortDeadwoodCards();
                upcard.keepCard((Card) bot.botDropCard());
            } else if (bot.botAction(upcard) == 2) {
                bot.takeStraightCard((Card) upcard.drawCard());
                bot.sortDeadwoodCards();
                upcard.keepCard((Card) bot.botDropCard());
            } else {
                root.getChildren().add(passText);
            }
            //change button pass to new
            buttonBox.getChildren().remove(btnPass);
            buttonBox.getChildren().add(btnNew);

            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            //Score 
            playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
            playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
            botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Deadwood())));
        });

        btnNew.setOnAction(event -> {
            player.takeDeadwoodCard((Card) drawpile.drawCard());
            if (firstPass == true) {
                root.getChildren().remove(passText);
            }

            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            //Score 
            playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
            playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
            botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Deadwood())));
        });

        startNewGame();
        player.sortDeadwoodCards();
        bot.sortDeadwoodCards();
        playerDropCard();

        //Score 
        playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
        playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
        botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Deadwood())));

        return root;
    }

    public void playerDropCard() {
        for (int i = 0; i < player.getDeadwoodSize(); i++) {
            final int index = i;
            player.getDeadwoodNode(i).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    final int selectedIndex = index;
                    event.consume();
                    System.out.println(selectedIndex);
                    upcard.keepCard((Card) player.getDeadwoodNode(index));
                }
            });
        }
    }

    private void startNewGame() {
        deck.refill();

        bot.reset();
        player.reset();

        bot.takeDeadwoodCard(new Card("h", "3"));
        bot.takeDeadwoodCard(new Card("s", "3"));
        bot.takeDeadwoodCard(new Card("h", "4"));
        bot.takeDeadwoodCard(new Card("s", "7"));
        bot.takeDeadwoodCard(new Card("h", "5"));
        bot.takeDeadwoodCard(new Card("h", "n"));
        bot.takeDeadwoodCard(new Card("c", "8"));
        bot.takeDeadwoodCard(new Card("s", "4"));
        bot.takeDeadwoodCard(new Card("s", "5"));
        bot.takeDeadwoodCard(new Card("h", "p"));
        //player and bot draw card
        for (int i = 0; i < 10; i++) {
            player.takeDeadwoodCard(deck.drawCard());
//            bot.takeDeadwoodCard(deck.drawCard());
        }

        //deck keep card
        for (int i = 0; i < 31; i++) {
            drawpile.keepCard(deck.drawCard());
        }

        //open top card
//        upcard.keepCard(deck.drawCard());
        upcard.keepCard(new Card("h", "6"));

        //print test
        System.out.println(player.toString());
        System.out.println(bot.toString());
        System.out.println(drawpile.toString());
        System.out.println(upcard.toString());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //MENU
        //set pane for menu
        Pane root = new Pane();
        root.setPrefSize(1000, 600);
        Pane rootLayout = new Pane();
        rootLayout.setPrefSize(1000, 600);

        //set black background
        Region background = new Region();
        background.setPrefSize(1000, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        //set menu background
        Rectangle menuBg = new Rectangle(1000, 600);
        Image imgBg = new Image("resources/background/bg3.jpg");
        menuBg.setFill(new ImagePattern(imgBg));

        //set text menu background
        Rectangle textGin = new Rectangle(950, 560);
        Image imgtextGin = new Image("resources/background/textginrummy.png");
        textGin.setFill(new ImagePattern(imgtextGin));

        //set button on menu
        Rectangle btnStart = new Rectangle(204, 420, 211, 70);
        Image imgBtnStart = new Image("resources/background/play.png");
        btnStart.setFill(new ImagePattern(imgBtnStart));

        Rectangle btnExit = new Rectangle(528, 420, 211, 70);
        Image imgBtnExit = new Image("resources/background/exit.png");
        btnExit.setFill(new ImagePattern(imgBtnExit));

        //add all to layout
        rootLayout.getChildren().addAll(menuBg, textGin, btnStart, btnExit);
        root.getChildren().addAll(background, rootLayout);

        //button action
        //start game
        btnStart.setOnMousePressed(event -> {
            primaryStage.setScene(new Scene(createGame()));
        });

        btnExit.setOnMousePressed(event -> {
            Platform.exit();
        });

        //set effect button
        btnStart.setOnMouseEntered(event -> {
            glow.setLevel(1.5);
            btnStart.setEffect(glow);
        });

        btnStart.setOnMouseExited(event -> {
            btnStart.setEffect(null);
        });

        btnExit.setOnMouseEntered(event -> {
            glow.setLevel(1.3);
            btnExit.setEffect(glow);
        });

        btnExit.setOnMouseExited(event -> {
            btnExit.setEffect(null);
        });

        primaryStage.setScene(new Scene(createGame()));
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("GinRummy");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
