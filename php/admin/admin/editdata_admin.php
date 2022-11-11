<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");
    
    $idData = $_POST["idData"];
    $id = $_POST["id"];
    $nama = $_POST["nama"];
    $email = $_POST["email"];

    $sql = "UPDATE admin SET id_admin = '$id', nama = '$nama',email = '$email' WHERE id_admin ='$idData'";

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