<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nim = $_POST["nim"];
    $nama = $_POST["nama"];
    $prodi = $_POST["prodi"];
    $semester = $_POST["semester"];
    $matkul1 = $_POST["matkul1"];
    $sksmatkul1 = $_POST["sksmatkul1"];
    $matkul2 = $_POST["matkul2"];
    $sksmatkul2 = $_POST["sksmatkul2"];
    $matkul3 = $_POST["matkul3"];
    $sksmatkul3 = $_POST["sksmatkul3"];
    $matkul4 = $_POST["matkul4"];
    $sksmatkul4 = $_POST["sksmatkul4"];
    $matkul5 = $_POST["matkul5"];
    $sksmatkul5 = $_POST["sksmatkul5"];
    $matkul6 = $_POST["matkul6"];
    $sksmatkul6 = $_POST["sksmatkul6"];
    $matkul7 = $_POST["matkul7"];
    $sksmatkul7 = $_POST["sksmatkul7"];
    $matkul8 = $_POST["matkul8"];
    $sksmatkul8 = $_POST["sksmatkul8"];
    $matkulbatal1 = $_POST["matkulbatal1"];
    $sksbatal1 = $_POST["sksbatal1"];
    $matkulbatal2 = $_POST["matkulbatal2"];
    $sksbatal2 = $_POST["sksbatal2"];
    $matkulbatal3 = $_POST["matkulbatal3"];
    $sksbatal3 = $_POST["sksbatal3"];
    $jumlahsks = $_POST["jumlahsks"];

    $sql = "INSERT INTO krs(nim,nama,prodi,semester,matkul1,jumlah_sks_matkul1,matkul2,jumlah_sks_matkul2,matkul3,jumlah_sks_matkul3,
            matkul4,jumlah_sks_matkul4,matkul5,jumlah_sks_matkul5,matkul6,jumlah_sks_matkul6,matkul7,jumlah_sks_matkul7,
            matkul8,jumlah_sks_matkul8,matkulbatal1,jumlah_batal_matkul1,matkulbatal2,jumlah_batal_matkul2,matkulbatal3,jumlah_batal_matkul3,
            jumlah_sks) VALUES ('$nim','$nama','$prodi','$semester','$matkul1','$sksmatkul1','$matkul2','$sksmatkul2','$matkul3','$sksmatkul3',
            '$matkul4','$sksmatkul4','$matkul5','$sksmatkul5','$matkul6','$sksmatkul6','$matkul7','$sksmatkul7',
            '$matkul8','$sksmatkul8','$matkulbatal1','$sksbatal1','$matkulbatal2','$sksbatal2','$matkulbatal3','$sksbatal3',
            '$jumlahsks')";
    $result = mysqli_query($con,$sql);
    $ceknim = "SELECT nim FROM krs WHERE nim='$_POST[nim]'";
    $prosescek = mysqli_query($con,$ceknim);

    if($result){
        echo "Pengisian KRS Berhasil";
        }else{
            echo "Pengisian KRS Error";
        }


?>