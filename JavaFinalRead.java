
/* Aaron E Whittle
ITC2001 Java Programming SEC 01

This program is supposed to extrapolate the data inputed in the JavaFinalMake program
to determine if a particular triathlete would be a good investment for title sponsorship
at the world championships in Kona.  
*/
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

import javax.swing.JOptionPane;

class Emprec {
  String name;
  String usat;  
  double placed;
  double notplaced;
  char sex;
  int age;


Emprec(){}

Emprec(String name,String usat,double p,double n)
{

this.name=name;
usat=usat;
placed=p;
notplaced=n;

}//Emprec






double calc_success_index(double placed,double notplaced)
{

  double success_index;

  success_index = placed / (placed + notplaced);

   if (success_index < .40) return (placed * notplaced * 125);

   else if ( success_index < .60) return (placed * notplaced * 110);

   else return (placed * notplaced * 100);


}// calculate the athelete's success index


double calc_total_race()
{

   return (placed + notplaced);

}// calculate total number of races


double calc_loss_prob(double placed, double notplaced)
{

  double loss_prob;

  loss_prob = notplaced / (placed + notplaced);

  return (loss_prob);

}// this is the athlete's loss probability thus far




public  String toString()
{
 NumberFormat fmt = NumberFormat.getInstance();
return 
("\n  Name: "+name+
"\n  USAT Number:"+usat+
"\n  Total Number of Races placed with the top ten: "+placed+
"\n  Total Number of Races placed outside of the top ten: "+notplaced+
"\n Success Index: "+fmt.format(calc_success_index())+
"\n Loss Probability "+fmt.format(calc_loss_prob( placed, notplaced)));


}//toString



}

public class JavaFinalRead extends Frame
             implements ActionListener {

 
Emprec employee;


   private TextField RecNumberField,  NameField,
                      DivisionField, PlacedField, DidNotPlaceField;


  

   private Button next,   // get next record in file
                  done;   // quit program

   // Application other pieces
   private DataInputStream input;

   // Constructor -- initialize the Frame 
   public JavaFinalRead()
   {
      super( "Candidate for Sponsorship to Kona" );

      // Open the file
      try {
         input = new DataInputStream(
                     new FileInputStream( "kona.dat" ) );
      }
      catch ( IOException e ) {
         //System.err.println( "File not opened properly\n" +
          //                   e.toString() );
		  
		   JOptionPane.showMessageDialog(null,
                "Error opening the file !!!!"+e.toString() ,
                "Error Window",
                JOptionPane.PLAIN_MESSAGE );
		  
         System.exit( 1 );
      }      

      setSize( 1024, 780 );
      setLayout( new GridLayout( 6, 2 ) );
     setFont(new Font("gothic",Font.BOLD,36));  

   
      //Label the fields in the frame
      add( new Label( "USAT Number" ) );
      RecNumberField = new TextField();
        RecNumberField.setEditable( false );
      add( RecNumberField );

      add( new Label( "Name" ) );
       NameField = new TextField( 20 );
         NameField.setEditable( false );
      add(  NameField );      

      add( new Label( "Division" ) );
       DivisionField = new TextField( 20 ); 
         DivisionField.setEditable( false );      
       add(DivisionField );

      add( new Label( "Races Finished in Top 10" ) );
      PlacedField = new TextField( 20 );
            PlacedField.setEditable( false );
      add( PlacedField );

      add( new Label( "Races Finished outside of Top 10" ) );
      DidNotPlaceField = new TextField( 20 );
      DidNotPlaceField.setEditable( false );
      add( DidNotPlaceField );

      //next = new Button( "Next" );
     // next.addActionListener( this );
      //add( next );      

	  
	  
	  
	  next = new Button("READ");
		next.addActionListener(this);
		next.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					readRecord();
			} // keyPressed
		}/*KeyAdapter*/ );
		add(next);
	  
	  
	  
	  
	  done = new Button("Close");
		done.addActionListener(this);
		done.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					closeFile();
			} // keyPressed
		}/*KeyAdapter*/ );
		add(done);
	  
	  
    //  done = new Button( "Done" );
    //  done.addActionListener( this );
    //  add( done );       
 
 

 
      setVisible( true );  
	   PlacedField.requestFocus();
	   addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			} // windowClosing
		}/*WindowAdapter*/ );
	 
   }
   
   
   
   

  public void actionPerformed( ActionEvent e )
   {
     if ( e.getSource() == next )
        readRecord();
     else
        closeFile();
  }

   public void readRecord()
   {
      int recnumber;
      String name, usat;
      double p,n;
 NumberFormat fmt = NumberFormat.getInstance();
      // input the values from the file
      try {
         recnumber = input.readInt();
         name = input.readUTF();
         usat = input.readUTF();
         p = input.readDouble();
         n = input.readDouble();
    
//Finally we make our object !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
athlete=new Emprec(name,division,p,n);
    
	System.out.println(athlete);
       

	   RecNumberField.setText( String.valueOf( recnumber ) );
         NameField.setText( name );
         DivisionField.setText( division );
         PlacedField.setText( String.valueOf( fmt.format(athlete.calc_state_tax(p,n) )) );
            DidNotPlaceField.setText( String.valueOf( fmt.format(athlete.calc_gross_pay() )) );

      }
      catch ( EOFException eof ) {
         closeFile();
      }
      catch ( IOException e ) {
         System.err.println( "Error during read from file\n" +
                             e.toString() );
         System.exit( 1 );
      }
   }

   private void closeFile()
   {
      try {
         input.close();
         System.exit( 0 );
      }
      catch ( IOException e ) {
         System.err.println( "Error closing file\n" +
                             e.toString() );
         System.exit( 1 );
      }
   }

   public static void main( String args[] )
   {
      new JavaFinalRead();
   }
}








