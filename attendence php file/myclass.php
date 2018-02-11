<?php

//Turn off Error Reporting
error_reporting(0);

	if (isset($_POST['tag']) && $_POST['tag'] != '') 
	{
		// get tag
		$tag = $_POST['tag'];
		
		// Request type is check Login
		$sub = $_POST['sub'];
		$dep = $_POST['dep'];
		$sec = $_POST['sec'];
                                      $yea= $_POST['yea'];
                                       $fr= $_POST['from'];
                                        $t= $_POST['to'];



		// response Array
		$response = array("tag" => $tag);
		
		// Connecting to mysql database
        $con = mysql_connect("localhost", "root", "") or die(mysql_error()); //server,user,password
 
        // Selecing database
        $db = mysql_select_db("attendence") or die(mysql_error()) or die(mysql_error());
		
		if ($tag == 'log') 
		{			
			$result = mysql_query("SELECT * FROM account WHERE username = '$username' AND password ='$password'") or die(mysql_error());
			
			// check for result 
			$no_of_rows = mysql_num_rows($result);
			
			 if ($no_of_rows > 0) 
			 {
				 $response["result"]="Valid User";
				 echo json_encode($response);

			 } 
			 else 
			 {
				// user not found
				$response["result"]="Username or Password Is Wrong!!!";
				echo json_encode($response);
			 }
				 
		}
                                             


                                         else if ($tag == 'create') 
		{	
			$result = mysql_query("INSERT INTO newclass (subject,department,section,year,rollfrom,rollto) VALUES('$fr','$dep','$sec','$yea','$fr','$t')");
			
			 if ($result) 
			 {
				 $response["result"]="Successfully Inserted.";
				 echo json_encode($response);
			 } 
			 else 
			 {
				// unable to insert
				$response["result"]="Unable To Insert";
				echo json_encode($response);
			 }
				 
			
		}

		
		else 
		{
			echo "Invalid Request";
		}	
	} 
	else 
	{
    echo "Access Denied";
	}

?>