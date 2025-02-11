package HomeGrownGrades;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class CustomPanel extends JPanel
   {
   CustomPanel ()
      {
      super ();
      setLayout (new FlowLayout (FlowLayout.CENTER, 10, 10));
      setBorder (new BevelBorder (BevelBorder.RAISED));
      ColorManager.addMember (this);
      }
   }