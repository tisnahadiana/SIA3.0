<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $id = $_POST["id"];

    $sql = "DELETE FROM admin WHERE id_admin = '$id'";

    $result = mysqli_query($con,$sql);


    if($result){
        $status['status'] = 'success';
        echo json_encode($status);
        mysqli_close($con);
    }
    else{
        $status['status'] = 'error';
        echo json_encode($status);
        mysqli_close($con);
    }


?>