/*
Aaron E Whittle
ITC2001

This program is made for an athlete who is submitting his results to the company
that will determine if they are a good investment for sponsorship
*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class JavaFinalMake extends Frame
             implements ActionListener
			 {

 
 
   private TextField RecNumberField,  NameField,
                      DivisionField, PlacedField, DidNotPlaceField;

   private Button enter,  
                  done;    

   private DataOutputStream output;  

   public JavaFinalMake()
   {
      super( "Enter Competitor's Information for Kona World Championships" );

        try {
         output = new DataOutputStream(
                      new FileOutputStream( "payroll.dat" ) );
      }
      catch ( IOException e ) {
         System.err.println( "File not opened properly\n" +
                             e.toString() );
         System.exit( 1 );
      }      

        
      setLayout( new GridLayout( 6, 2 ) );
     setFont(new Font("verdana",Font.BOLD,36));  

      // create the labels
      add( new Label( "USAT Number" ) );
      RecNumberField = new TextField();
      add( RecNumberField );
	  

      add( new Label( "Name" ) );
       NameField = new TextField( 20 );
      add(  NameField );      

      add( new Label( "Division" ) );
       DivisionField = new TextField( 20 );
      add(  DivisionField );

      add( new Label( "Races Finished in Top 10" ) );
      PlacedField = new TextField( 20 );
      add( PlacedField );

      add( new Label( "Races Finished outisde of Top 10" ) );
      DidNotPlaceField = new TextField( 20 );
      add( DidNotPlaceField );
	  
	  
	  

	  
	  
	    enter = new Button("Enter");
		enter.addActionListener(this);
		enter.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					addRecord();
			} // keyPressed
		}/*KeyAdapter*/ );
		add(enter);
	  
	     

	  
	  
	   done = new Button("Done");
		done.addActionListener(this);
		done.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					closeFile();
			} // keyPressed
		}/*KeyAdapter*/ );
		add(done);

   setVisible( true );  
   addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			} // windowClosing
		}/*WindowAdapter*/ );
   }



   public void actionPerformed( ActionEvent e )
   {
       if ( e.getSource() == enter ) addRecord();

      if ( e.getSource() == done ) closeFile();
	  
	  
   }// actionPerformed

 private void closeFile()
   {
      try {
         output.close();
         System.exit( 0 );
      }
      catch ( IOException e ) {
         System.err.println( "Error closing file\n" +
                             e.toString() );
         System.exit( 1 );
      }
   }


   public void addRecord()
   {
      int RecNumber = 0;
      Double h,r;
	 
      if ( ! RecNumberField.getText().equals( "" ) ) {

         // output the values to the file
         try {
            RecNumber =
               Integer.parseInt( RecNumberField.getText() );

            if ( RecNumber > 0 ) {
               output.writeInt( RecNumber );
               output.writeUTF(  NameField.getText() );
               output.writeUTF(  DivisionField.getText() );
			   
			 			   
               h = new Double ( PlacedField.getText() );
               output.writeDouble( h.doubleValue() );
               
			   r = new Double ( DidNotPlaceField.getText() );
               output.writeDouble( r.doubleValue() );

              }

            // clear the TextFields
            RecNumberField.setText( "" );
             NameField.setText( "" );
             DivisionField.setText( "" );
            PlacedField.setText( "" );
               DidNotPlaceField.setText("");   
         }
         catch ( NumberFormatException nfe ) {
            System.err.println(
               "You must enter an integer account number" );
         }
         catch ( IOException io ) {
            System.err.println(
               "Error during write to file\n" +
               io.toString() );
            System.exit( 1 );
         }
      }
   }


   public static void main( String args[] )
   {
      new JavaFinalMake();
   }
}


