package eyegaze.gui;

public class Tes {
	
	 private static int magic_counter=777;

     public static void mymain() {   // <=== We will call this 
         System.out.println("Hello, World in java from mymain");
         System.out.println(magic_counter);
     }
     
     public static int mymain2(int n) {   // <== add this new function
         for (int i=0; i<n; i++)  {    
             System.out.print (i);
             System.out.println("Hello, World !");
         }
         return n*2;                    // return twice the param
     }

}
