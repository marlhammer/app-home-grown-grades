package HomeGrownGrades;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class BackupDialog extends JDialog
   {
   private JComboBox drives;
   private JList dirsList;
   private DefaultListModel dirs = new DefaultListModel ();
   private JButton back;
   private JTextField message;
   private String currentPath;
   private Vector currentDirs = new Vector ();
   private final File [] roots = File.listRoots ();

   BackupDialog (String title)
      {
      super ();
      Container ROOT = getContentPane ();
      ROOT.setLayout (new BorderLayout ());
      ROOT.setBackground (Color.black);
      setModal (true);
      setLocationRelativeTo (null);
      setTitle (Constants.title + " - " + title);
      setSize (325, 325);
      setResizable (false);
      addWindowListener
         (
         new WindowAdapter () { public void windowClosing (WindowEvent event) { shutDown (); } }
         );

      CustomPanel selector = new CustomPanel ();
         selector.setLayout (new BorderLayout ());
         JPanel messageHolder = new JPanel ();
            messageHolder.setOpaque (false);
            message = new JTextField (25);
               message.setBackground (Color.white);
               message.setEditable (false);
               messageHolder.add (message);
            selector.add (messageHolder, BorderLayout.NORTH);
         JPanel dirsHolder = new JPanel ();
            dirsHolder.setOpaque (false);
            dirsList = new JList (dirs);
               dirsList.addMouseListener
                  (
                  new MouseAdapter ()
                     {
                     public void mouseClicked (MouseEvent e)
                        {
                        if (e.getClickCount () == 2)
                           {
                           int selection = dirsList.locationToIndex (e.getPoint());
                           if (selection != -1)
                              {
                              File tempFile = (File) currentDirs.get(selection);
                              currentPath = tempFile.getPath ();
                              if (tempFile.getParent() == null) { back.setEnabled (false); }
                              else { back.setEnabled (true); }
                              refreshDirectories ();
                              }
                           }
                        }
                     }
                  );
            JScrollPane dirsPane = new JScrollPane (dirsList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
               dirsPane.setPreferredSize (new Dimension (275, 150));
               dirsHolder.add (dirsPane);
            selector.add (dirsHolder, BorderLayout.CENTER);
         JPanel drivesHolder = new JPanel ();
            drivesHolder.setOpaque (false);
            back = new JButton ("Back");
               back.setEnabled (false);
               back.addActionListener
                  (
                  new ActionListener ()
                     {
                     public void actionPerformed (ActionEvent event)
                        {
                        File tempFile = new File (currentPath);
                        String parent = tempFile.getParent ();
                        if (tempFile.getParentFile().getParent() == null) { back.setEnabled (false); }
                        if (parent != null)
                           {
                           currentPath = parent;
                           refreshDirectories ();
                           }
                        }
                     }
                  );
               drivesHolder.add (back);
            drives = new JComboBox (roots);
               drives.addActionListener
                  (
                  new ActionListener ()
                     {
                     public void actionPerformed (ActionEvent e)
                        {
                        currentPath = roots[drives.getSelectedIndex ()].getPath ();
                        refreshDirectories ();
                        back.setEnabled (false);
                        }
                     }
                  );
               drivesHolder.add (drives);
            selector.add (drivesHolder, BorderLayout.SOUTH);
         ROOT.add (selector, BorderLayout.CENTER);

      CustomPanel controlPanel = new CustomPanel ();
         JButton ok = new JButton ("OK");
            ok.addActionListener
               (
               new ActionListener () { public void actionPerformed (ActionEvent event) { dispose (); } }
               );
            controlPanel.add (ok);
         JButton cancel = new JButton ("Cancel");
            cancel.addActionListener
               (
               new ActionListener () { public void actionPerformed (ActionEvent event) { shutDown (); } }
               );
            controlPanel.add (cancel);
         ROOT.add (controlPanel, BorderLayout.SOUTH);

      currentDirs.clear ();
      for (int index = 0; index < roots.length; ++index) { currentDirs.addElement (roots[index]); }
      }

   public void show ()
      {
      if (currentPath == null) { currentPath = roots[drives.getSelectedIndex ()].getPath (); }
      refreshDirectories ();
      super.show ();
      }

   String getPath () { return currentPath; }

   private void refreshDirectories ()
      {
      message.setText (" " + currentPath + " ");
      dirs.clear ();
      currentDirs.clear ();
      if (currentPath != null)
         {
         File temp = new File (currentPath);
         File [] subDirectories = temp.listFiles ();
         if (subDirectories != null)
            {
            for (int index = 0; index < subDirectories.length; ++index)
               {
               if (subDirectories[index].isDirectory () == true)
                  {
                  currentDirs.add (subDirectories[index]);
                  dirs.addElement (" " + subDirectories[index].getName () + " ");
                  }
               }
            }
         }
      }

   private void shutDown ()
      {
      currentPath = null;
      dispose ();
      }

   }