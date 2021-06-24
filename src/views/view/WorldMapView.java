package views.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DimensionUIResource;

import engine.City;
import views.button.CityButton;
import views.panel.CardsPanel;
import views.panel.ImagePanel;
import views.panel.PlayerPanel;

public class WorldMapView extends JFrame {
  private static final String COLOR_BEIGE = "#C8AE81";
  private JPanel armyPanel = new JPanel();
  private JPanel citiesPanel = new JPanel();
  private PlayerPanel playerPanel;
  private JPanel armies = new JPanel();
  private CityButton cairoButton;
  private CityButton romeButton;
  private CityButton spartaButton;
  private CardsPanel armyCards;

  public CardsPanel getArmyCards() {
    return armyCards;
  }

  public void setArmyCards(CardsPanel armyCards) {
    this.armyCards = armyCards;
  }

  public JPanel getArmies() {
    return armies;
  }

  public CityButton getSpartaButton() {
    return spartaButton;
  }

  public void setSpartaButton(CityButton spartaButton) {
    this.spartaButton = spartaButton;
  }

  public CityButton getRomeButton() {
    return romeButton;
  }

  public void setRomeButton(CityButton romeButton) {
    this.romeButton = romeButton;
  }

  public CityButton getCairoButton() {
    return cairoButton;
  }

  public void setCairoButton(CityButton cairoButton) {
    this.cairoButton = cairoButton;
  }

  public void setArmies(JPanel armies) {
    this.armies = armies;
  }

  public WorldMapView(ActionListener a, PlayerPanel playerpanel) {
    this.playerPanel = playerpanel;
    setExtendedState(MAXIMIZED_BOTH);
    setVisible(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle("The Conqueror");
    pack();
    getContentPane().add(playerPanel, BorderLayout.PAGE_START);
    getContentPane().add(armyPanel, BorderLayout.PAGE_END);
    addArmyPane();
    getContentPane().add(citiesPanel, BorderLayout.CENTER);
    addCitiesPane(a);

  }

  public JPanel getCitiesPanel() {
    return citiesPanel;
  }

  public void setCitiesPanel(JPanel citiesPanel) {
    this.citiesPanel = citiesPanel;
  }

  public PlayerPanel getPlayerPanel() {
    return playerPanel;
  }

  public void setPlayerPanel(PlayerPanel playerPanel) {
    this.playerPanel = playerPanel;
  }

  public JPanel getArmyPanel() {
    return armyPanel;
  }

  public void setArmyPanel(JPanel armyPanel) {
    this.armyPanel = armyPanel;
  }

  public void addArmyPane() {
    armyPanel.setLayout(new BorderLayout());
    armyPanel.setPreferredSize(new DimensionUIResource(WIDTH, 400));
    armyPanel.setBackground(Color.decode(COLOR_BEIGE));
    StyledLabel label =  new StyledLabel("Controlled Armies", 25, true);
     label.setBorder(new EmptyBorder(10,10,10,10));
    armyPanel.add(label, BorderLayout.PAGE_START);

    armyCards = new CardsPanel();
    armyCards.setPreferredSize(new DimensionUIResource(500,JFrame.HEIGHT));
    armyPanel.add(armyCards, BorderLayout.LINE_START);

  }

  public void addCitiesPane(ActionListener a) {
    citiesPanel.setLayout(new BorderLayout());
    ImagePanel cairoPanel = new ImagePanel(new ImageIcon("src/images/maxresdefault.jpg").getImage());
    ImagePanel romePanel = new ImagePanel(new ImageIcon("src/images/rome.jpg").getImage());
    ImagePanel spartaPanel = new ImagePanel(new ImageIcon("src/images/gdfg.jpg").getImage());

    cairoPanel.setLayout(new BorderLayout());
    cairoPanel.add(new StyledLabel("Cairo", 70, true), BorderLayout.LINE_START);
    cairoButton = new CityButton("view", 20);
    cairoButton.addActionListener(a);
    JPanel buttonArea = new JPanel();
    buttonArea.setLayout(new BorderLayout());
    buttonArea.add(cairoButton, BorderLayout.EAST);
    buttonArea.setOpaque(false);
    cairoPanel.add(buttonArea, BorderLayout.SOUTH);

    spartaPanel.setLayout(new BorderLayout());
    StyledLabel spartaName = new StyledLabel("Sparta", 70, true);
    spartaName.setForeground(Color.WHITE);
    spartaButton = new CityButton("view", 20);
    spartaButton.setActionCommand("sparta");
    spartaButton.addActionListener(a);
    JPanel buttonArea2 = new JPanel();
    buttonArea2.setLayout(new BorderLayout());
    buttonArea2.add(spartaButton, BorderLayout.EAST);
    buttonArea2.setOpaque(false);
    spartaPanel.add(buttonArea2, BorderLayout.SOUTH);
    spartaPanel.add(spartaName, BorderLayout.LINE_START);

    romePanel.setLayout(new BorderLayout());
    StyledLabel romeName = new StyledLabel("Rome", 70, true);
    romeName.setForeground(Color.WHITE);
    romePanel.add(romeName, BorderLayout.LINE_START);
    romeButton = new CityButton("view", 20);
    JPanel buttonArea3 = new JPanel();
    buttonArea3.setLayout(new BorderLayout());
    buttonArea3.add(romeButton, BorderLayout.EAST);
    buttonArea3.setOpaque(false);
    romePanel.add(buttonArea3, BorderLayout.SOUTH);
    romeButton.addActionListener(a);

    romePanel.setPreferredSize(new DimensionUIResource(600, JFrame.HEIGHT));
    spartaPanel.setPreferredSize(new DimensionUIResource(600, JFrame.HEIGHT));
    citiesPanel.add(romePanel, BorderLayout.WEST);
    citiesPanel.add(cairoPanel, BorderLayout.CENTER);
    citiesPanel.add(spartaPanel, BorderLayout.EAST);

    cairoButton.setActionCommand("Cairo");
    spartaButton.setActionCommand("Sparta");
    romeButton.setActionCommand("Rome");
  }

  public void enableButton(City city) {
    String name = city.getName();
    if (name.equals("Cairo")) {
      cairoButton.setEnabled(true);
    } else if (name.equals("Sparta")) {
      spartaButton.setEnabled(true);
    } else {
      romeButton.setEnabled(true);
    }
  }
}
