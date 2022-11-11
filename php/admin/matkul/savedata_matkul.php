<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nama_mk = $_POST["nama_mk"];
    $sks = $_POST["sks"];
    $prodi = $_POST["prodi"];
    $semester = $_POST["semester"];

    $sql = "INSERT INTO daftarmatkul(nama_mk,sks,prodi,semester) VALUES ('$nama_mk','$sks','$prodi','$semester')";

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