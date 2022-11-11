<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");
    
    $nidnData = $_POST["nidnData"];
    $nidn = $_POST["nidn"];
    $nama = $_POST["nama"];
    $email = $_POST["email"];
    $prodi = $_POST["prodi"];
    $semester = $_POST["semester"];

    $sql = "UPDATE walidosen SET nidn = '$nidn', nama = '$nama',email = '$email',prodi = '$prodi', semester = '$semester' WHERE nidn ='$nidnData'";

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