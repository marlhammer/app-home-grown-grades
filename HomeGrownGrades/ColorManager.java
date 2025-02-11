package HomeGrownGrades;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class ColorManager
   {
   private static LinkedList members = new LinkedList ();
   private static Color currentColor = Color.gray;

   static void addMember (JComponent newMember)
      {
      if (newMember != null)
         {
         members.add (newMember);
         newMember.setBackground (currentColor);
         }
      }

   static void changeColor (Color newColor)
      {
      if (newColor != null)
         {
         for (int index = 0; index < members.size(); ++index)
            {
            JComponent current = (JComponent) members.get (index);
            current.setBackground (newColor);
            }
         currentColor = newColor;
         }
      }

   static Color getColor () { return currentColor; }
   }