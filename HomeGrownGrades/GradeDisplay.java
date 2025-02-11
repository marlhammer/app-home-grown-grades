package HomeGrownGrades;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class GradeDisplay extends JTextArea
   {
   void copyText ()
      {
      this.selectAll ();
      this.copy ();
      }

   void clear ()
      {
      this.selectAll ();
      this.replaceSelection (null);
      }

   boolean displayGrades (String title, double [] gradeSet, double average, int weight, boolean weightStatus)
      {
      clear ();
      append (title + "\n\n");
      if (gradeSet[0] == -1)
         {
         if (weightStatus == true && weight > 0)
            {
            append ("Weight: " + weight + "\n\n");
            append ("To see weighted average you need to add a grade or set the weight to zero");
            return false;
            }
         return true;
         }
      append ("Average: " + Constants.NF.format (average) + "\n");
      if (weightStatus == true) { append ("Weight: " + weight + "\n"); }
      append ("\n");
      for (int index = 0; index < gradeSet.length; ++index)
         {
         if (gradeSet[index] == -1) { break; }
         else { append ("#" + (index + 1) + "   " + gradeSet[index] + '\n'); }
         }
      if (weightStatus == true && weight == 0)
         {
         append ("\nTo see weighted average you need to set the weight for this category");
         return false;
         }
      setCaretPosition (0);
      return true;
      }

   GradeDisplay (int rows, int columns)
      {
      super (rows, columns);
      setBorder
         (
         new CompoundBorder
            (
            new BevelBorder (BevelBorder.LOWERED),
            new LineBorder (Color.darkGray, 5)
            )
         );
      setBackground (Color.darkGray);
      setForeground (Color.white);
      setFont (new Font ("Courier New", Font.PLAIN, 13));
      setEditable (false);
      setLineWrap (true);
      setWrapStyleWord (true);
      setSelectedTextColor (Color.black);
      setSelectionColor (Color.white);
      }
   }