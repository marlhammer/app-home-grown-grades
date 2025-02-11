package HomeGrownGrades;

import java.text.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class Constants
   {
   static final int standardWidth = 640;
   static final int standardHeight = 480;
   static final int studentLimit = 144;
   static final int subjectLimit = 12;
   static final int [] gradeLimits = { 360, 120, 180, 120};
   static final int categoryLimit = 4;
   static final String [] categoryNames = {"Homework", "Test", "Quiz", "Other Work"};
   static final NumberFormat NF = NumberFormat.getInstance ();
   static final String copyright = new String ("Copyright 2002 by Stephen Mouring, Jr.\nAll rights reserved.\n");
   static final String title = new String (" Home Grown Grades");

   static { NF.setMaximumFractionDigits (2); }
   }