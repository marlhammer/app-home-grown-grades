package HomeGrownGrades;

import javax.swing.*;
import java.awt.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class UserInterface
   {
   private static final String errorMessage = new String ("That is an INVALID entry");

   static void showMessage (String message) { JOptionPane.showMessageDialog (null, message, Constants.title, JOptionPane.INFORMATION_MESSAGE); }

   static int getGradeNumber (String message)
      {
      for (;;)
         {
         try
            {
            String input = JOptionPane.showInputDialog (null, message, Constants.title, JOptionPane.INFORMATION_MESSAGE);
            if (input == null) { return -1; }
            if (input.startsWith ("#")) { input = input.replace ('#', ' '); }
            input = input.trim ();
            int result = (new Integer (input).intValue ());
            return result;
            }
         catch (NumberFormatException e) { showMessage (errorMessage); }
         }
      }

   static int getPercentage (String message)
      {
      for (;;)
         {
         try
            {
            String input = JOptionPane.showInputDialog (null, message, Constants.title, JOptionPane.INFORMATION_MESSAGE);
            if (input == null) { return -1; }
            if (input.endsWith ("%")) { input = input.replace ('%', ' '); }
            input = input.trim ();
            int result = (new Integer (input).intValue ());
            if (result >= 0 && result <= 100) { return result; }
            else { showMessage ("Please enter a value between 0 and 100"); }
            }
         catch (NumberFormatException e) { showMessage (errorMessage); }
         }
      }

   static double getGrade (String message)
      {
      for (;;)
         {
         try
            {
            String input = JOptionPane.showInputDialog (null, message, Constants.title, JOptionPane.INFORMATION_MESSAGE);
            if (input == null) { return -1; }
            if (input.endsWith ("%")) { input = input.replace ('%', ' '); }
            input = input.trim ();
            double result = (new Double(input).doubleValue());
            if (result >= 0 && result <= 200) { return result; }
            else { showMessage ("Please enter a value between 0 and 200"); }
            }
         catch (NumberFormatException e) { showMessage (errorMessage); }
         }
      }

   static String getString (String message)
      {
      for (;;)
         {
         String input = JOptionPane.showInputDialog (null, message, Constants.title, JOptionPane.INFORMATION_MESSAGE);
         if (input == null) { return null; }
         input = input.trim ();
         if (input.equals ("") == true) { showMessage (errorMessage); }
         else { return input; }
         }
      }

   static int getYesNoCancel (String message)
      {
      return JOptionPane.showConfirmDialog (null, message, Constants.title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
      }

   static boolean getOkCancel (String message)
      {
      int temp = JOptionPane.showConfirmDialog (null, message, Constants.title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
      if (temp == JOptionPane.OK_OPTION) { return true; }
      return false;
      }

   static Color getColor ()
      {
      return JColorChooser.showDialog (null, Constants.title, Color.gray);
      }
   }