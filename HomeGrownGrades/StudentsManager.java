package HomeGrownGrades;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.security.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class StudentsManager
   {
   private final String gradesFile = new String ("grades.ccc");
   private final String backupFile = new String ("backupGrades.ccc");

   private Student [] students = new Student [Constants.studentLimit];
   private int studentCount = 0;

   StudentsManager ()
      {
      loadStudents ();
      for (int index = 0; index < students.length; ++index)
         {
         if (students[index] != null) { ++studentCount; }
         else { return; }
         }
      }

   boolean areStudentsNull () { return students[0] == null; }

   int getStudentCount () { return studentCount; }

   Student [] getStudents () { return students; }

   Student getStudent (int key)
      {
      if (key >= 0 && key < students.length) { return students[key]; }
      return null;
      }

   boolean newStudent ()
      {
      if (students[Constants.studentLimit - 1] != null)
         {
         UserInterface.showMessage ("You have entered all the students that you can");
         return false;
         }
      boolean cleared = false;
      while (cleared == false)
         {
         String name = UserInterface.getString ("Please enter the new student NAME");
         if (name == null) { return false; }
         for (int index = 0; index < students.length; ++index)
            {
            if (students[index] != null)
               {
               if (students[index].getName().equals (name) == true)
                  {
                  UserInterface.showMessage ("There is already a student with this name");
                  break;
                  }
               }
            else
               {
               ChangeManager.madeChange ();
               students[index] = new Student (name);
               ++studentCount;
               return true;
               }
            }
         }
      return false;
      }

   boolean deleteStudent (int number)
      {
      if (number >= 0 && number < students.length)
         {
         if (UserInterface.getOkCancel ("Are you sure you want to delete " + students[number].getName () + "?"))
            {
            ChangeManager.madeChange ();
            students[number] = null;
            --studentCount;
            for (int index = number; index < students.length; ++index)
               {
               int next = index + 1;
               if (next < students.length && students[next] != null)
                  {
                  students[index] = students[next];
                  students[next] = null;
                  }
               }
            return true;
            }
         return false;
         }
      return false;
      }

   void changeStudentName (int number)
      {
      if (areStudentsNull ()) { return; }
      if (number >= 0 && number < students.length)
         {
         boolean cleared = false;
         while (cleared == false)
            {
            String newName = UserInterface.getString ("Please enter the new name for " + students[number].getName ());
            if (newName == null) { return; }
            for (int index = 0; index < students.length; ++index)
               {
               if (students[index] != null)
                  {
                  if (students[index].getName().equals (newName) == true)
                     {
                     UserInterface.showMessage ("There is already a student with this name");
                     break;
                     }
                  }
               else
                  {
                  ChangeManager.madeChange ();
                  students[number].setName (newName);
                  return;
                  }
               }
            }
         }
      }

   private void loadStudents ()
      {
      try
         {
         FileInputStream FIS = new FileInputStream (gradesFile);
         ObjectInputStream OIS = new ObjectInputStream (FIS);
         students = (Student[]) OIS.readObject ();
         OIS.close ();
         }
      catch (FileNotFoundException e) { return; }
      catch (Throwable e) { e.printStackTrace (); }
      }

   void saveStudents ()
      {
      try
         {
         FileOutputStream FOS = new FileOutputStream  (gradesFile);
         ObjectOutputStream OOS = new ObjectOutputStream (FOS);
         OOS.writeObject (students);
         OOS.close();
         ChangeManager.savedChanges ();
         }
      catch (IOException e) { e.printStackTrace (); }
      }

   void makeBackup ()
      {
      BackupDialog BD = new BackupDialog ("MAKE BACKUP");
      String path;
      boolean cleared = false;
      while (cleared == false)
         {
         BD.show ();
         path = BD.getPath ();
         if (path == null) { return; }
         saveStudents ();
         ChangeManager.savedChanges ();
         try
            {
            FileOutputStream FOS = new FileOutputStream (path + "\\" + backupFile);
            ObjectOutputStream OOS = new ObjectOutputStream (FOS);
            OOS.writeObject (students);
            OOS.close();
            UserInterface.showMessage ("Backup was CREATED");
            cleared = true;
            }
         catch (FileNotFoundException e)
            {
            String totalPath = new String (path + "\\" + backupFile);
            String result = e.getMessage().substring(totalPath.length());
            UserInterface.showMessage ("ERROR   ---   " + result.trim());
            }
         catch (IOException e)
            {
            e.printStackTrace ();
            UserInterface.showMessage ("An error occurred and the backup was NOT created\n\nPlease contact techincal support");
            }
         }
      }

   boolean restoreBackup ()
      {
      BackupDialog BD = new BackupDialog ("RESTORE BACKUP");
      String path;
      for (;;)
         {
         BD.show ();
         path = BD.getPath ();
         if (path == null) { return false; }
         try
            {
            FileInputStream FIS = new FileInputStream (path + "\\" + backupFile);
            ObjectInputStream OIS = new ObjectInputStream (FIS);
            students = (Student[]) OIS.readObject ();
            OIS.close ();
            UserInterface.showMessage ("Backup was RESTORED");
            return true;
            }
         catch (FileNotFoundException e)
            {
            String totalPath = new String (path + "\\" + backupFile);
            String result = e.getMessage().substring(totalPath.length());
            UserInterface.showMessage ("ERROR   ---   " + result.trim());
            }
         catch (Throwable e)
            {
            e.printStackTrace ();
            UserInterface.showMessage ("An error occurred and the backup was NOT restored\n\nPlease contact techincal support");
            }
         }
      }
   }