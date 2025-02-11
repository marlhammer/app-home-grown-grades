package HomeGrownGrades;

import java.io.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class Student implements Serializable
   {
   private String name;
   private Subject [] subjects;
   private int subjectCount = 0;

   Student (String name)
      {
      this.name = name;
      subjects = new Subject [Constants.subjectLimit];
      }

   String getName () { return name; }

   void setName (String name) { this.name = name; }

   boolean newSubject ()
      {
      if (subjects[Constants.subjectLimit - 1] != null)
         {
         UserInterface.showMessage ("You have entered all the subjects that you can");
         return false;
         }
      boolean cleared = false;
      while (cleared == false)
         {
         String name = UserInterface.getString (" Please enter the new subject NAME");
         if (name == null) { return false; }
         for (int index = 0; index < subjects.length; ++index)
            {
            if (subjects[index] != null)
               {
               if (subjects[index].getName().equals (name) == true)
                  {
                  UserInterface.showMessage ("There is already a subject with this name");
                  break;
                  }
               }
            else
               {
               ChangeManager.madeChange ();
               subjects[index] = new Subject (name);
               ++subjectCount;
               return true;
               }
            }
         }
      return false;
      }

   boolean deleteSubject (int number)
      {
      if (number >= 0 && number < subjects.length)
         {
         if (UserInterface.getOkCancel ("Are you sure you want to delete " + subjects[number].getName () + "?"))
            {
            ChangeManager.madeChange ();
            subjects[number] = null;
            --subjectCount;
            for (int index = number; index < subjects.length; ++index)
               {
               int next = index + 1;
               if (next < subjects.length && subjects[next] != null)
                  {
                  subjects[index] = subjects[next];
                  subjects[next] = null;
                  }
               }
            return true;
            }
         return false;
         }
      return false;
      }

   void changeSubjectName (int number)
      {
      if (number >= 0 && number < subjects.length)
         {
         boolean cleared = false;
         while (cleared == false)
            {
            String newName = UserInterface.getString ("Please enter the new name for " + subjects[number].getName ());
            if (newName == null) { return; }
            for (int index = 0; index < subjects.length; ++index)
               {
               if (subjects[index] != null)
                  {
                  if (subjects[index].getName().equals (newName) == true)
                     {
                     UserInterface.showMessage ("There is already a subject with this name");
                     break;
                     }
                  }
               else
                  {
                  ChangeManager.madeChange ();
                  subjects[number].setName (newName);
                  return;
                  }
               }
            }
         }
      }

   boolean areSubjectsNull () { return subjects[0] == null; }

   int getSubjectCount () { return subjectCount; }

   Subject [] getSubjects () { return subjects; }

   Subject getSubject (int number)
      {
      if (number >= 0 && number < subjects.length) { return subjects[number]; }
      return null;
      }
   }