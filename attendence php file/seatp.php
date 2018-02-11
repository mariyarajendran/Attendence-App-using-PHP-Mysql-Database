
<?php

define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','movie');
 
$con = mysqli_connect(HOST,USER,PASS,DB);


					 $response["error"] = 1;	
	
$sql = "select seatposition from booking b, temp t where b.movie_id=t.movie_id AND b.showtime=t.showtime";

$res = mysqli_query($con,$sql);
 
 
 	 
							 
 
                             $result = array();
							 $response['selected_seat'] = array();
                      while($row = mysqli_fetch_array($res)) {
                          $mark=explode(':', $row['seatposition']);
						  
						  
						   $result['timeanddate'] = $res['dateandtime'];
                           foreach($mark as $out) {
							
							 $result['selected'] = $out;
							 
							  array_push($response['selected_seat'],$result);
				
							 }
							 
						
							 
							}
                      
                                       
							
							  $response['error'] = 0;
							  
							  	
							  
							  echo json_encode($response); 
							  
							 
							  
							  
							  	
			
		
		
					
		
			
		$response["result"]="delete";
				echo json_encode($response);
		
		
			               


						
							   
							
mysqli_close($con);
 
?>

