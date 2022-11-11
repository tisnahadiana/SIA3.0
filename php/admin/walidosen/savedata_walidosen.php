<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nidn = $_POST["nidn"];
    $nama = $_POST["nama"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $prodi = $_POST["prodi"];
    $semester = $_POST["semester"];

    $sql = "INSERT INTO walidosen(nidn,nama,email,password,prodi,semester) VALUES ('$nidn','$nama','$email','$password','$prodi','$semester')";

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