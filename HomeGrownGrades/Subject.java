package HomeGrownGrades;

import java.io.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class Subject implements Serializable
   {
   private String name;
   private double [][] grades = new double [Constants.categoryLimit][];
   private int [] weights = new int [Constants.categoryLimit];
   private boolean weightStatus = false;

   Subject (String name)
      {
      this.name = name;
      for (int row = 0; row < grades.length; ++row)
         {
         grades[row] = new double [Constants.gradeLimits[row]];
         for (int column = 0; column < grades[row].length; ++column) { grades[row][column] = -1; }
         }
      }

   boolean isAnySetValid ()
      {
      for (int index = 0; index < grades.length; ++index)
         {
         if (grades[index][0] > -1) { return true; }
         }
      return false;
      }

   boolean isSetValid (int type)
      {
      if (type >= 0 && type <= 3)
         {
         if (grades[type][0] == -1) { return false; }
         return true;
         }
      return false;
      }

   String getName () { return name; }

   void setName (String name) { this.name = name; }

   boolean getWeightStatus () { return weightStatus; }

   void setWeightStatus (boolean newStatus) { weightStatus = newStatus; }

   int [] getWeights () { return weights; }

   int getWeightSum () { return weights[0] + weights[1] + weights[2] + weights[3]; }

   void setWeight (int key)
      {
      if (key >= 0 && key <= 3)
         {
         int newWeight = UserInterface.getPercentage ("Please enter the new " + Constants.categoryNames[key].toUpperCase() + " weight");
         if (newWeight == -1) { return; }
         ChangeManager.madeChange ();
         weights[key] = newWeight;
         }
      }

   double [][] getGrades () { return grades; }

   void newGrade (int type)
      {
      if (type < 0 || type > 3) { return; }
      double value = UserInterface.getGrade ("Please enter the new GRADE");
      if (value == -1) { return; }
      for (int index = 0; index < grades[type].length; ++index)
         {
         if (grades[type][index] < 0)
            {
            ChangeManager.madeChange ();
            grades[type][index] = value;
            return;
            }
         }
      UserInterface.showMessage ("You have entered all the grades possible for this category");
      }

   void deleteGrade (int type)
      {
      if (type < 0 || type > 3) { return; }
      int number = getGradeNumber (type);
      if (number == -1) { return; }
      if (grades[type][number] != -1)
         {
         ChangeManager.madeChange ();
         grades[type][number] = -1;
         for (int index = number; index < grades[type].length; ++index)
            {
            int next = index + 1;
            if (next < grades[type].length && grades[type][next] != -1)
               {
               grades[type][index] = grades[type][next];
               grades[type][next] = -1;
               }
            }
         }
      }

   void changeGrade (int type)
      {
      if (type < 0 || type > 3) { return; }
      int number = getGradeNumber (type);
      if (number == -1) { return; }
      if (grades[type][number] != -1)
         {
         double newValue = UserInterface.getGrade ("Please enter the NEW VALUE for this grade");
         if (newValue == -1) { return; }
         ChangeManager.madeChange ();
         grades[type][number] = newValue;
         }
      }

   private int getGradeNumber (int type)
      {
      int number = 0;
      boolean cleared = false;
      while (cleared == false)
         {
         number = UserInterface.getGradeNumber ("Please enter the grade NUMBER") - 1;
         if (number < 0) { return -1; }
         else
            {
            if (number > grades[type].length || grades[type][number] == -1) { UserInterface.showMessage ("That grade does not exist"); }
            else { cleared = true; }
            }
         }
      return number;
      }
   }