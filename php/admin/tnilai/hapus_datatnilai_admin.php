<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nim = $_POST["nim"];

    $sql = "DELETE FROM tnilai WHERE nim = '$nim'";

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