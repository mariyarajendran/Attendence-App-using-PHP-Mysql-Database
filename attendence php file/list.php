<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','attendence');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
 $response["error"]="welcome"
 
$sql = "select * from seat";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('rollno'=>$row[1]
));
}

 echo json_decode($response);
 
//echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>