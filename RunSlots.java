// ****************************************************************
// RunSlots.java               Slots
//
// Author: Adam Wang
//
// Simulates a slot game.
// ****************************************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RunSlots extends JFrame
{
  private JButton spinButton, addButton; //buttons
  private JLabel balanceLabel, dialogLabel, label1, label2, label3; //text and image holders
  private ImageIcon cherry, lemon, melon, grape, apple, orange; //fruit images
  
  private int credits = 100; //starting credits
  
  public RunSlots()
  {
    //load images
    cherry = new ImageIcon(getClass().getResource("images/cherry.jpg"));
    lemon = new ImageIcon(getClass().getResource("images/lemon.jpg"));
    melon = new ImageIcon(getClass().getResource("images/melon.jpg"));
    grape = new ImageIcon(getClass().getResource("images/grape.jpg"));
    apple = new ImageIcon(getClass().getResource("images/apple.jpg"));
    orange = new ImageIcon(getClass().getResource("images/orange.jpg"));
    
    //set up GUI
    setTitle("Slot Machine");
    setSize(490,400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    getContentPane().setBackground(Color.white);
    createGUI();
  }
  
  private void createGUI() //helps set up the GUI
  {
    JPanel panel = new JPanel();
    panel.setOpaque(false);
    panel.setLayout(null);
    add(panel);
    
    //shows balance
    balanceLabel = new JLabel();
    balanceLabel.setFont(new Font("Arial", Font.PLAIN, 25));
    balanceLabel.setBounds(25,300,150,25);
    panel.add(balanceLabel);
    updateBalance(); //updates text
    
    //shows fruit #1
    label1 = new JLabel(cherry);
    label1.setBounds(10,10,150,150);
    panel.add(label1);
    
    //shows fruit #2
    label2 = new JLabel(cherry);
    label2.setBounds(160,10,150,150);
    panel.add(label2);
    
    //shows fruit #3
    label3 = new JLabel(cherry);
    label3.setBounds(310,10,150,150);
    panel.add(label3);
    
    //shows dialog
    dialogLabel = new JLabel();
    dialogLabel.setFont(new Font("Arial", Font.PLAIN, 25));
    dialogLabel.setBounds(80,175,300,40);
    dialogLabel.setText("Press SPIN! to play.");
    dialogLabel.setHorizontalAlignment(SwingConstants.CENTER);
    dialogLabel.setVerticalAlignment(SwingConstants.CENTER);
    panel.add(dialogLabel);
    
    //button for spinning the slots
    spinButton = new JButton("SPIN!");
    spinButton.setFont(new Font("Arial", Font.BOLD, 20));
    spinButton.setBackground(Color.red); 
    spinButton.setBounds(185, 280, 100, 60);
    spinButton.addActionListener(new spinAction()); //executes spinAction() upon being pressed
    panel.add(spinButton);
    
    //button for adding more credits
    addButton = new JButton("Add Credits");
    addButton.setFont(new Font("Arial", Font.PLAIN, 15));
    addButton.setBounds(320, 300, 120, 25);
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) //overrides ActionListener interface method when pressed
      {
        credits += 50;
        updateBalance();
        dialogLabel.setText("50 credits added.");
      }
    });
    panel.add(addButton);
  }
  
  private void updateBalance() //updates text on balanceLabel to reflect credits
  {
    balanceLabel.setText("Credits: " + credits);
  }
  
  private String spinHelper()
  {
    JLabel[] slots = {label1, label2, label3};

    int roll; // 0=cherry, 1=lemon, 2=melon, 3=grape, 4=apple, 5=orange
    int[] fruit = {0,0,0,0,0,0}; //counts the number of each type of fruit
    
    for (JLabel f : slots)
    {
      roll = (int)(Math.random()*6); //random fruit is selected
      if (roll==0) {
        f.setIcon(cherry);
        fruit[0]++;
      } else if (roll==1) {
        f.setIcon(lemon);
        fruit[1]++;
      } else if (roll==2) {
        f.setIcon(melon);
        fruit[2]++;
      } else if (roll==3) {
        f.setIcon(grape);
        fruit[3]++;
      } else if (roll==4) {
        f.setIcon(apple);
        fruit[4]++;
      } else {
        f.setIcon(orange);
        fruit[5]++;
      }
    }
    
    for (int i=0; i<6; i++) //determines if a fruit occurs twice or thrice
    {
      if (fruit[i]==3)
      {
        credits += 30;
        updateBalance();
        return "TRIPLE! +30 credits.";
      }
      if (fruit[i]==2)
      {
        credits += 10;
        updateBalance();
        return "DOUBLE! +10 credits.";
      }
    }
    credits -= 10;
    updateBalance();
    return "No Combo. -10 credits.";
  }
  
  public static void main(String[] args)
  {
    System.out.println("Bet Amount: 10 credits. Doubles earn 10 credits. Triples earn 30 credits. Press SPIN! to bet.");
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new RunSlots().setVisible(true); //Creates a new RunSlots JFrame
      }
    });
  }
  
  private class spinAction implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e) //Overrides interface method when SPIN! JButton is pressed
    {
      if (credits<10)
        dialogLabel.setText("Insufficient credits.");
      else
        dialogLabel.setText(spinHelper()); //spinHelper() randomizes the fruit and returns the output
    }
  }
}