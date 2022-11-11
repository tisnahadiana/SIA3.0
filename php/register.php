<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nim = $_POST["nim"];
    $nama = $_POST["nama"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $prodi = $_POST["prodi"];
    $semester = $_POST["semester"];

    $sql = "INSERT INTO mahasiswa(nim,nama,email,password,prodi,semester) VALUES ('$nim','$nama','$email','$password','$prodi','$semester')";
    $result = mysqli_query($con,$sql);

    if($result){
        echo "registered successfully";
    }
    else{
        echo "some error occured";
    }


?>