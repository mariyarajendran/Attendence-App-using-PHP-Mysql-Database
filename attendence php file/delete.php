<?php

//Turn off Error Reporting
error_reporting(0);

	if (isset($_POST['tag']) && $_POST['tag'] != '') 
	{
		// get tag
		$tag = $_POST['tag'];
		
		// Request type is check Login
		$deleteid = $_POST['deleteid'];
		

		// response Array
		$response = array("tag" => $tag);
		
		// Connecting to mysql database
        $con = mysql_connect("localhost", "root", "") or die(mysql_error()); //server,user,password
 
        // Selecing database
        $db = mysql_select_db("attendence") or die(mysql_error()) or die(mysql_error());
		
		if ($tag == 'delete') 
		{			
			$result = mysql_query("DELETE   FROM newclass WHERE id = '$deleteid'") or die(mysql_error());
			
		 $response["result"]="$deleteid";
				 echo json_encode($response);
			/*// check for result 
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
				 */
		}
                                             


                                         else if ($tag == 'create') 
		{	
			$result = mysql_query("INSERT INTO account(username,password,name,rank,department,phone_number) VALUES('$username','$password','$name','$rank','$department','$phone_number')");
			
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