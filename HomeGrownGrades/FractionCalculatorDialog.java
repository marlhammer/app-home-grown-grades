package HomeGrownGrades;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class FractionCalculatorDialog extends JDialog
   {
   private JTextField correct;
   private JTextField possible;
   private JLabel answerLabel;

   FractionCalculatorDialog ()
      {
      super ();
      Container ROOT = getContentPane ();
      ROOT.setLayout (new GridLayout (4, 1));
      ROOT.setBackground (Color.black);
      setModal (false);
      setLocationRelativeTo (null);
      setTitle (Constants.title);
      setSize (275, 225);
      setResizable (false);
      addWindowListener
         (
         new WindowAdapter ()
            {
            public void windowClosing (WindowEvent event) { dispose (); }
            public void windowClosed (WindowEvent event) {}
            }
         );

      CustomPanel resultPanel = new CustomPanel ();
         answerLabel = new JLabel ("Enter the fraction");
            answerLabel.setForeground (Color.black);
            answerLabel.setFont (new Font ("Courier New", Font.BOLD, 15));
            resultPanel.add (answerLabel);
         ROOT.add (resultPanel);
      CustomPanel correctPanel = new CustomPanel ();
         JLabel cLabel = new JLabel ("Total points CORRECT");
            cLabel.setForeground (Color.black);
            correctPanel.add (cLabel);
         correct = new JTextField (10);
            correct.setBackground (Color.white);
            correct.setForeground (Color.black);
            correctPanel.add (correct);
         ROOT.add (correctPanel);
      CustomPanel possiblePanel = new CustomPanel ();
         JLabel pLabel = new JLabel ("Total points POSSIBLE");
            pLabel.setForeground (Color.black);
            possiblePanel.add (pLabel);
         possible = new JTextField (10);
            possible.setBackground (Color.white);
            possible.setForeground (Color.black);
            possiblePanel.add (possible);
         ROOT.add (possiblePanel);
      CustomPanel controls = new CustomPanel ();
         JButton calculate = new JButton ("CALCULATE");
            calculate.setMnemonic ('C');
            calculate.addActionListener
               (
               new ActionListener () { public void actionPerformed (ActionEvent event) { calculate (); } }
               );
            controls.add (calculate);
         JButton close = new JButton ("CLOSE");
            close.addActionListener
               (
               new ActionListener ()
                  {
                  public void actionPerformed (ActionEvent event) { dispose (); }
                  }
               );
            controls.add (close);
         ROOT.add (controls);
      }

   private void calculate ()
      {
      try
         {
         double valueC = (new Double (correct.getText ()).doubleValue ());
         double valueP = (new Double (possible.getText ()).doubleValue ());
         double answer = (valueC / valueP) * 100;
         clear ();
         if (answer == Double.POSITIVE_INFINITY || answer == Double.NEGATIVE_INFINITY)
            {
            answerLabel.setText (" Infinity ");
            return;
            }
         answerLabel.setText (" Grade = " + Constants.NF.format (answer) + " ");
         }
      catch (NumberFormatException error)
         {
         clear ();
         answerLabel.setText ("Invalid entries");
         }
      }

   private void clear ()
      {
      answerLabel.setText (null);
      possible.selectAll ();
      possible.replaceSelection (null);
      correct.selectAll ();
      correct.replaceSelection (null);
      }
   }