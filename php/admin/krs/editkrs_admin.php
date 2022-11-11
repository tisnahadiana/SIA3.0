<?php
    $con=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nimData = $_POST["nimData"];
    $namaData = $_POST["namaData"];
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
    $date = $_POST["date"];

    $sql = "UPDATE krs SET nim = '$nim', nama = '$nama', prodi = '$prodi', semester = '$semester', matkul1 = '$matkul1', jumlah_sks_matkul1 = '$sksmatkul1',
    matkul2 = '$matkul2', jumlah_sks_matkul2 = '$sksmatkul2', matkul3 = '$matkul3', jumlah_sks_matkul3 = '$sksmatkul3', matkul4 = '$matkul4', jumlah_sks_matkul4 = '$sksmatkul4',
    matkul5 = '$matkul5', jumlah_sks_matkul5 = '$sksmatkul5', matkul6 = '$matkul6', jumlah_sks_matkul6 = '$sksmatkul6', matkul7 = '$matkul7', jumlah_sks_matkul7 = '$sksmatkul7',
    matkul8 = '$matkul8', jumlah_sks_matkul8 = '$sksmatkul8', matkulbatal1 = '$matkulbatal1', jumlah_batal_matkul1 = '$sksbatal1', matkulbatal2 = '$matkulbatal2', jumlah_batal_matkul2 = '$sksbatal2',
    matkulbatal3 = '$matkulbatal3', jumlah_batal_matkul3 = '$sksbatal3', jumlah_sks = '$jumlahsks', date = '$date' WHERE nim ='$nimData' AND nama ='$namaData'";

    $result = mysqli_query($con,$sql);
    $ceknim = "SELECT nim FROM krs WHERE nim='$_POST[nim]'";
    $prosescek = mysqli_query($con,$ceknim);

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