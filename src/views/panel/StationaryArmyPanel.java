package views.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import engine.City;
import units.Army;
import views.button.ArmyButton;

public class StationaryArmyPanel extends JPanel {
  private Army army;
  private JTextArea info = new JTextArea();
  private ArmyButton selectArmy;
  private City city;

  public StationaryArmyPanel(ActionListener a, Army army) {
    this.army = army;
    setLayout(new BorderLayout());
    selectArmy = new ArmyButton("Select", 20);
    selectArmy.setArmy(army);
    JPanel buttonPanel = new JPanel();
    JPanel panel1 = new JPanel();
    panel1.setLayout(new BorderLayout());

    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.add(selectArmy);
    panel1.add(buttonPanel, BorderLayout.SOUTH);
    add(panel1, BorderLayout.PAGE_END);
    info.setText("");
    add(info);
    selectArmy.addActionListener(a);
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public Army getArmy() {
    return army;
  }

  public ArmyButton getAction1() {
    return selectArmy;
  }

  public void setAction(ArmyButton action) {
    this.selectArmy = action;
  }

  public JTextArea getInfo() {
    return info;
  }

  public void setInfo(JTextArea info) {
    this.info = info;
  }

  public void setArmy(Army army) {
    this.army = army;
  }

}
