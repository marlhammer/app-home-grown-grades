package HomeGrownGrades;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class ChangeManager
   {
   private static boolean unsavedChange = false;

   static void madeChange () { unsavedChange = true; }

   static void savedChanges () { unsavedChange = false; }

   static boolean getStatus () { return unsavedChange; }
   }