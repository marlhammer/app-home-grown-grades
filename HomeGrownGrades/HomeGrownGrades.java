package HomeGrownGrades;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

public class HomeGrownGrades extends JFrame
   {
   private final String dataFile = new String ("programData.ccc");
   private final GradeDisplay [] displays = new GradeDisplay [Constants.categoryLimit];
   private final HelpDialog HD;
   private StudentsManager SM;
   private PasswordManager PM;
   private JComboBox studentList;
   private JComboBox subjectList;
   private JMenuItem deleteStudent;
   private JMenuItem changeStudentName;
   private CustomJMenu subjectsMenu;
   private JMenuItem deleteSubject;
   private JMenuItem changeSubjectName;
   private CustomPanel gradeControls;
   private CustomButtonMenu deleteGrade;
   private CustomButtonMenu changeGrade;
   private CustomButtonMenu changeWeight;
   private CustomPanel averagePanel;
   private JLabel averageLabel;
   private JButton averageButton;
   private CustomJMenu edit;
   private int width = 0;
   private int height = 0;
   private Student currentStudent = null;
   private Subject currentSubject = null;
   private int currentStudentPos = -1;
   private int currentSubjectPos = -1;
   private boolean onSubject = false;

   public static void main (String [] args)
      {
      System.out.println (Constants.copyright);
      new HomeGrownGrades ();
      }

   public HomeGrownGrades ()
      {
      super (Constants.title);
      loadProgramData ();
      Container ROOT = getContentPane ();
      ROOT.setLayout (new BorderLayout ());
      ROOT.setBackground (Color.black);
      setSize (width, height);
      setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
      addWindowListener
         (
         new WindowAdapter () { public void windowClosing (WindowEvent event) { closeProgram (); } }
         );
      addComponentListener
         (
         new ComponentAdapter ()
            {
            public void componentResized (ComponentEvent event)
               {
               Component temp = event.getComponent ();
               int newWidth = temp.getWidth ();
               int newHeight = temp.getHeight ();
               if (newWidth < Constants.standardWidth) { newWidth = Constants.standardWidth; }
               if (newHeight < Constants.standardHeight) { newHeight = Constants.standardHeight; }
               temp.setSize (newWidth, newHeight);
               }
            }
         );
      HD = new HelpDialog (this);
      SM = new StudentsManager ();

      JMenuBar JMB = new JMenuBar ();
         JMB.setBorder (new BevelBorder (BevelBorder.RAISED));
         JMB.setLayout (new FlowLayout (FlowLayout.LEFT));
         ColorManager.addMember (JMB);
         CustomJMenu file = new CustomJMenu ("File", 'F');
            JMenuItem save = new JMenuItem ("Save");
               save.setMnemonic ('S');
               save.addActionListener
                  (
                  new ActionListener () { public void actionPerformed (ActionEvent event) { SM.saveStudents (); } }
                  );
               file.add (save);
            JMenuItem exit = new JMenuItem ("Exit");
               exit.setMnemonic ('X');
               exit.addActionListener
                  (
                  new ActionListener () { public void actionPerformed (ActionEvent event) { closeProgram(); } }
                  );
               file.add (exit);
            JMenu backup = new JMenu ("Backup");
               JMenuItem makeBackup = new JMenuItem ("Make Backup");
                  makeBackup.addActionListener
                     (
                     new ActionListener () { public void actionPerformed (ActionEvent event) { SM.makeBackup (); } }
                     );
                  backup.add (makeBackup);
               JMenuItem restoreBackup = new JMenuItem ("Restore Backup");
                  restoreBackup.addActionListener
                     (
                     new ActionListener ()
                        {
                        public void actionPerformed (ActionEvent event)
                           {
                           if (SM.restoreBackup ())
                              {
                              currentStudent = null;
                              currentSubject = null;
                              refreshStudents ();
                              refreshSubjects ();
                              }
                           }
                        }
                     );
                  backup.add (restoreBackup);
               file.add (backup);
            JMB.add (file);
         edit = new CustomJMenu ("Edit", 'E');
            JMenu copyText = new JMenu ("Copy Text");
               copyText.setMnemonic ('C');
               JMenuItem [] copyItems = new JMenuItem [Constants.categoryLimit];
               for (int index = 0; index < copyItems.length; ++index)
                  {
                  final int temp = index;
                  copyItems[index] = new JMenuItem (Constants.categoryNames[index]);
                  copyItems[index].addActionListener
                     (
                     new ActionListener () { public void actionPerformed (ActionEvent event) { displays[temp].copyText (); } }
                     );
                  copyText. add (copyItems[index]);
                  }
               edit.add (copyText);
            JMB.add (edit);
         CustomJMenu tools = new CustomJMenu ("Tools", 'T');
            JMenuItem fractionCalculator = new JMenuItem ("Fraction Calculator");
               fractionCalculator.setMnemonic ('F');
               fractionCalculator.addActionListener
                  (
                  new ActionListener () { public void actionPerformed (ActionEvent event) { new FractionCalculatorDialog().show (); } }
                  );
               tools.add (fractionCalculator);
            JMenu passwordControls = PM.getPasswordMenu ();
               tools.add (passwordControls);
            JMenu colors = new JMenu ("Set Background Color");
               colors.add (new ColorMenuItem ("Gray", Color.gray));
               colors.add (new ColorMenuItem ("Blue", Color.blue.darker()));
               colors.add (new ColorMenuItem ("Red", Color.red.darker()));
               colors.add (new ColorMenuItem ("Green", Color.green.darker()));
               colors.add (new ColorMenuItem ("Cyan", Color.cyan.darker()));
               colors.add (new ColorMenuItem ("Magenta", Color.magenta.darker()));
               colors.add (new ColorMenuItem ("Orange", Color.orange.darker()));
               JMenuItem custom = new JMenuItem ("CUSTOM COLOR");
                  custom.addActionListener
                     (
                     new ActionListener () { public void actionPerformed (ActionEvent event) { ColorManager.changeColor (UserInterface.getColor ()); } }
                     );
                  colors.add (custom);
               tools.add (colors);
            JMB.add (tools);
         CustomJMenu help = new CustomJMenu ("Help", 'H');
            JMenuItem helpManual = new JMenuItem ("Help Manual");
               helpManual.addActionListener
                  (
                  new ActionListener () { public void actionPerformed (ActionEvent event) { HD.show (); } }
                  );
               help.add (helpManual);
            JMenuItem copyright = new JMenuItem ("Copyright");
               copyright.addActionListener
                  (
                  new ActionListener () { public void actionPerformed (ActionEvent event) { UserInterface.showMessage (Constants.copyright); } }
                  );
               help.add (copyright);
            JMB.add (help);
         CustomJMenu studentsMenu = new CustomJMenu ("Students");
            studentsMenu.setForeground (Color.white);
            JMenuItem newStudent = new JMenuItem ("New");
               newStudent.addActionListener
                  (
                  new ActionListener ()
                     {
                     public void actionPerformed (ActionEvent event)
                        {
                        if (SM.newStudent ())
                           {
                           refreshStudents ();
                           studentList.setSelectedIndex (SM.getStudentCount ());
                           }
                        }
                     }
                  );
               studentsMenu.add (newStudent);
            deleteStudent = new JMenuItem ("Delete");
               deleteStudent.addActionListener
                  (
                  new ActionListener ()
                     {
                     public void actionPerformed (ActionEvent event)
                        {
                        if (SM.deleteStudent (currentStudentPos))
                           {
                           currentStudent = null;
                           refreshStudents ();
                           }
                        }
                     }
                  );
               studentsMenu.add (deleteStudent);
            changeStudentName = new JMenuItem ("Change Name");
               changeStudentName.addActionListener
                  (
                  new ActionListener ()
                     {
                     public void actionPerformed (ActionEvent event)
                        {
                        int tempStudent = currentStudentPos + 1;
                        int tempSubject = 0;
                        if (onSubject == true) { tempSubject = currentSubjectPos + 1; }
                        SM.changeStudentName (currentStudentPos);
                        refreshStudents ();
                        studentList.setSelectedIndex (tempStudent);
                        if (tempSubject != 0)
                           {
                           refreshSubjects ();
                           subjectList.setSelectedIndex (tempSubject);
                           }
                        }
                     }
                  );
               studentsMenu.add (changeStudentName);
            JMB.add (studentsMenu);
         subjectsMenu = new CustomJMenu ("Subjects");
            subjectsMenu.setForeground (Color.white);
            JMenuItem newSubject = new JMenuItem ("New");
               newSubject.addActionListener
                  (
                  new ActionListener ()
                     {
                     public void actionPerformed (ActionEvent event)
                        {
                        if (currentStudent != null)
                           {
                           if (currentStudent.newSubject ())
                              {
                              refreshSubjects ();
                              subjectList.setSelectedIndex (currentStudent.getSubjectCount());
                              }
                           }
                        }
                     }
                  );
               subjectsMenu.add (newSubject);
            deleteSubject = new JMenuItem ("Delete");
               deleteSubject.addActionListener
                  (
                  new ActionListener ()
                     {
                     public void actionPerformed (ActionEvent event)
                        {
                        if (currentStudent != null)
                           {
                           if (currentStudent.deleteSubject (currentSubjectPos))
                              {
                              currentSubject = null;
                              refreshSubjects ();
                              }
                           }
                        }
                     }
                  );
               subjectsMenu.add (deleteSubject);
            changeSubjectName = new JMenuItem ("Change Name");
               changeSubjectName.addActionListener
                  (
                  new ActionListener ()
                     {
                     public void actionPerformed (ActionEvent event)
                        {
                        if (currentSubject != null)
                           {
                           int temp = currentSubjectPos + 1;
                           currentStudent.changeSubjectName (currentSubjectPos);
                           refreshSubjects ();
                           subjectList.setSelectedIndex (temp);
                           }
                        }
                     }
                  );
               subjectsMenu.add (changeSubjectName);
            JMB.add (subjectsMenu);
         setJMenuBar (JMB);

      CustomPanel listPanel = new CustomPanel ();
         studentList = new JComboBox ();
            studentList.addActionListener
               (
               new ActionListener ()
                  {
                  public void actionPerformed (ActionEvent e)
                     {
                     currentStudentPos = studentList.getSelectedIndex ();
                     if (currentStudentPos == 0)
                        {
                        currentStudent = null;
                        refreshSubjects ();
                        subjectsMenu.setEnabled (false);
                        currentStudentPos = -1;
                        currentSubjectPos = -1;
                        deleteStudent.setEnabled (false);
                        changeStudentName.setEnabled (false);
                        }
                     else
                        {
                        currentStudentPos -= 1;
                        currentStudent = SM.getStudent (currentStudentPos);
                        refreshSubjects ();
                        subjectsMenu.setEnabled (true);
                        deleteStudent.setEnabled (true);
                        changeStudentName.setEnabled (true);
                        }
                     }
                  }
               );
            listPanel.add (studentList);
         subjectList = new JComboBox ();
            subjectList.addActionListener
               (
               new ActionListener ()
                  {
                  public void actionPerformed (ActionEvent e)
                     {
                     currentSubjectPos = subjectList.getSelectedIndex ();
                     if (currentSubjectPos == 0)
                        {
                        currentSubjectPos = -1;
                        onSubject = false;
                        for (int index = 0; index < displays.length; ++ index) { displays[index].clear (); }
                        }
                     else
                        {
                        currentSubjectPos -= 1;
                        if (currentStudent != null) { currentSubject = currentStudent.getSubject (currentSubjectPos); }
                        onSubject = true;
                        setAverageControls ();
                        setGradeControls ();
                        refreshDisplays ();
                        }
                     edit.setEnabled (onSubject);
                     averagePanel.setVisible (onSubject);
                     gradeControls.setVisible (onSubject);
                     deleteSubject.setEnabled (onSubject);
                     changeSubjectName.setEnabled (onSubject);
                     }
                  }
               );
            listPanel.add (subjectList);
         ROOT.add (listPanel, BorderLayout.NORTH);

      JPanel centerHolder = new JPanel ();
         centerHolder.setOpaque (false);
         centerHolder.setLayout (new BorderLayout ());
         gradeControls = new CustomPanel ();
            gradeControls.setVisible (false);
            gradeControls.add (new CustomButtonMenu ("ADD GRADE", 1));
            deleteGrade = new CustomButtonMenu ("DELETE GRADE", 2);
               gradeControls.add (deleteGrade);
            changeGrade = new CustomButtonMenu ("CHANGE GRADE", 3);
               gradeControls.add (changeGrade);
            changeWeight = new CustomButtonMenu ("CHANGE WEIGHTS", 4);
               gradeControls.add (changeWeight);
            centerHolder.add (gradeControls, BorderLayout.NORTH);
         CustomPanel displayPanel = new CustomPanel ();
            displayPanel.setLayout (new GridLayout (1, Constants.categoryLimit));
            for (int index = 0; index < displays.length; ++index) { displays[index] = new GradeDisplay (0, 0); }
            JScrollPane [] SPs = new JScrollPane [Constants.categoryLimit];
            for (int index = 0; index < SPs.length; ++index)
               {
               SPs[index] = new JScrollPane (displays[index], JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
               ColorManager.addMember (SPs[index]);
               displayPanel.add (SPs[index]);
               }
            SPs[0].setBorder (new EmptyBorder (5, 5, 5, 2));
            SPs[1].setBorder (new EmptyBorder (5, 2, 5, 2));
            SPs[2].setBorder (new EmptyBorder (5, 2, 5, 2));
            SPs[3].setBorder (new EmptyBorder (5, 2, 5, 5));
            centerHolder.add (displayPanel, BorderLayout.CENTER);
         averagePanel = new CustomPanel ();
            averageButton = new JButton ();
               averageButton.addActionListener
                  (
                  new ActionListener ()
                     {
                     public void actionPerformed (ActionEvent event)
                        {
                        if (currentSubject != null)
                           {
                           boolean currentStatus = currentSubject.getWeightStatus ();
                           if (currentStatus == false) { currentSubject.setWeightStatus (true); }
                           else { currentSubject.setWeightStatus (false); }
                           setAverageControls ();
                           refreshDisplays ();
                           }
                        }
                     }
                  );
               averagePanel.add (averageButton);
            averageLabel = new JLabel ();
               averageLabel.setForeground (Color.white);
               averagePanel.add (averageLabel);
            centerHolder.add (averagePanel, BorderLayout.SOUTH);
         ROOT.add (centerHolder, BorderLayout.CENTER);

      PM.startupPassword ();
      refreshStudents ();
      refreshSubjects ();
      setVisible (true);
      }

   private void setAverageControls ()
      {
      if (currentSubject != null)
         {
         boolean weightStatus = currentSubject.getWeightStatus ();
         averageLabel.setVisible (weightStatus);
         changeWeight.setEnabled (weightStatus);
         if (weightStatus == false) { averageButton.setText ("WEIGHTING IS OFF"); return; }
         else
            {
            averageButton.setText ("WEIGHTING IS ON");
            averageLabel.setText (null);
            }
         }
      }

   private void setGradeControls ()
      {
      if (currentSubject != null)
         {
         boolean temp = currentSubject.isAnySetValid ();
         deleteGrade.setEnabled (temp);
         changeGrade.setEnabled (temp);
         }
      }

   private void refreshStudents ()
      {
      studentList.removeAllItems ();
      studentList.addItem (" (none selected)");
      deleteStudent.setEnabled (false);
      changeStudentName.setEnabled (false);
      if (SM.areStudentsNull ()) { studentList.setEnabled (false); return; }
      else { studentList.setEnabled (true); }
      Student [] students = SM.getStudents ();
      for (int index = 0; index < students.length; ++index)
         {
         if (students[index] != null) { studentList.addItem (" " + students[index].getName ()); }
         else { return; }
         }
      }

   private void refreshSubjects ()
      {
      for (int index = 0; index < displays.length; ++ index) { displays[index].clear (); }
      subjectList.removeAllItems ();
      subjectList.addItem (" (none selected)");
      deleteSubject.setEnabled (false);
      changeSubjectName.setEnabled (false);
      if (currentStudent != null)
         {
         if (currentStudent.areSubjectsNull()) { subjectList.setEnabled (false); return; }
         else { subjectList.setEnabled (true); }
         Subject [] tempSubjects = currentStudent.getSubjects ();
         for (int index = 0; index < tempSubjects.length; ++index)
            {
            if (tempSubjects[index] != null) { subjectList.addItem (" " + tempSubjects[index].getName ()); }
            else { return; }
            }
         }
      else { subjectList.setEnabled (false); }
      }

   private void refreshDisplays ()
      {
      if (currentSubject != null)
         {
         double [][] grades = currentSubject.getGrades ();
         double [] averages = new double [Constants.categoryLimit];
         for (int row = 0; row < grades.length; ++row)
            {
            int count = 0;
            for (int column = 0; column < grades[row].length; ++column)
               {
               if (grades[row][column] != -1)
                  {
                  ++count;
                  averages[row] += grades[row][column];
                  }
               else { break; }
               }
            if (count != 0) { averages[row] /= count; }
            else { averages[row] = -1; }
            }
         int [] weights = currentSubject.getWeights ();
         boolean weightStatus = currentSubject.getWeightStatus ();
         boolean noAverage = false;
         for (int index = 0; index < Constants.categoryLimit; ++index)
            {
            String title = new String (Constants.categoryNames[index].toUpperCase() + " GRADES");
            if (displays[index].displayGrades (title, grades[index], averages[index], weights[index], weightStatus) == false) { noAverage = true; }
            }
         if (weightStatus == false) { return; }
         else
            {
            if (currentSubject.getWeightSum () != 100)
               {
               averageLabel.setVisible (true);
               averageLabel.setText ( "The weights do not add up to 100 percent");
               return;
               }
            else { averageLabel.setVisible (false); }
            if (noAverage == false)
               {
               double total = 0;
               for (int index = 0; index < averages.length; ++index)
                  {
                  if (averages[index] != -1)
                     {
                     averages[index] *= weights[index];
                     total += averages[index];
                     }
                  }
               total /= 100;
               averageLabel.setVisible (true);
               averageLabel.setText ("Weighted grade average is " + Constants.NF.format (total));
               }
            }
         }
      }

   private void closeProgram ()
      {
      saveProgramData ();
      if (ChangeManager.getStatus() == true)
         {
         int value = UserInterface.getYesNoCancel ("Would you like to SAVE your work?");
         switch (value)
            {
            case 0: { SM.saveStudents (); }
            case 1: { dispose (); System.exit (0); }
            default: { return; }
            }
         }
      dispose ();
      System.exit (0);
      }

   private void loadProgramData ()
      {
      try
         {
         FileInputStream FIS = new FileInputStream (dataFile);
         ObjectInputStream OIS = new ObjectInputStream (FIS);
         width = OIS.readInt ();
         height = OIS.readInt ();
         boolean passwordStatus = OIS.readBoolean ();
         String password = (String) OIS.readObject ();
         Color color = (Color) OIS.readObject ();
         OIS.close ();
         PM = new PasswordManager (password, passwordStatus);
         ColorManager.changeColor (color);
         }
      catch (FileNotFoundException e)
         {
         width = Constants.standardWidth;
         height = Constants.standardHeight;
         PM = new PasswordManager (null, false);
         return;
         }
      catch (Throwable e) { e.printStackTrace (); }
      }

   private void saveProgramData ()
      {
      try
         {
         FileOutputStream FOS = new FileOutputStream (dataFile);
         ObjectOutputStream OOS = new ObjectOutputStream (FOS);
         OOS.writeInt (getWidth());
         OOS.writeInt (getHeight());
         OOS.writeBoolean (PM.getPasswordStatus ());
         OOS.writeObject (PM.getPassword ());
         OOS.writeObject (ColorManager.getColor());
         OOS.close();
         }
      catch (IOException e) { e.printStackTrace (); }
      }

   private class CustomJMenu extends JMenu
      {
      CustomJMenu (String name)
         {
         super (name);
         ColorManager.addMember (this);
         }

       CustomJMenu (String name, char mnemonic)
         {
         this (name);
         setMnemonic (mnemonic);
         }
      }

   private class ColorMenuItem extends JMenuItem
      {
      private Color color;

      ColorMenuItem (String title, Color inputColor)
         {
         super (title);
         color = inputColor;
         addActionListener
            (
            new ActionListener () { public void actionPerformed (ActionEvent event) { ColorManager.changeColor (color); } }
            );
         }
      }

   private class CustomButtonMenu extends JButton
      {
      private int type;
      private JPopupMenu categoryMenu;
      private JMenuItem [] categories;

      CustomButtonMenu (String name, int type)
         {
         super (name);
         this.type = type;
         addActionListener
            (
            new ActionListener () { public void actionPerformed (ActionEvent event) { showMenu (); } }
            );

         categoryMenu = new JPopupMenu ();
            categories = new JMenuItem [Constants.categoryLimit];
            for (int index = 0; index < categories.length; ++index)
               {
               final int temp = (index + 1);
               categories[index] = new JMenuItem (Constants.categoryNames[index]);
               categories[index].addActionListener
                  (
                  new ActionListener () { public void actionPerformed (ActionEvent event) { performTask (temp); } }
                  );
               categoryMenu.add (categories[index]);
               }
         }

      private void showMenu ()
         {
         if (currentSubject != null)
            {
            if (type != 1 && type != 4)
               {
               for (int index = 0; index < categories.length; ++index)
                  {
                  if (currentSubject.isSetValid (index) == false) { categories[index].setEnabled (false); }
                  else { categories[index].setEnabled (true); }
                  }
               }
            }
         categoryMenu.show (this, this.getWidth(), 0);
         }

      private void performTask (int key)
         {
         if (currentSubject != null)
            {
            switch (type)
               {
               case 1: { currentSubject.newGrade (key - 1); break; }
               case 2: { currentSubject.deleteGrade (key - 1); break; }
               case 3: { currentSubject.changeGrade (key - 1); break; }
               case 4: { currentSubject.setWeight (key - 1); break; }
               }
            refreshDisplays ();
            setGradeControls ();
            }
         }
      }
   }