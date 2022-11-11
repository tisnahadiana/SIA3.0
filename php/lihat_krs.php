<?php
    $connect=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");

    $nim = $_POST["nim"];
    $nama = $_POST["nama"];

    $sql = "SELECT * FROM krs WHERE nim = '$nim' AND nama = '$nama'";
    $result = array();
    $result['data'] = array();
    $responce = mysqli_query($connect,$sql);

    if(mysqli_num_rows($responce) === 1){
        $row = mysqli_fetch_assoc($responce);
        $ds['nim'] = $row['nim'];
        $ds['nama'] = $row['nama'];
        $ds['prodi'] = $row['prodi'];
        $ds['semester'] = $row['semester'];
        $ds['matkul1'] = $row['matkul1'];
        $ds['sks1'] = $row['jumlah_sks_matkul1'];
        $ds['matkul2'] = $row['matkul2'];
        $ds['sks2'] = $row['jumlah_sks_matkul2'];
        $ds['matkul3'] = $row['matkul3'];
        $ds['sks3'] = $row['jumlah_sks_matkul3'];
        $ds['matkul4'] = $row['matkul4'];
        $ds['sks4'] = $row['jumlah_sks_matkul4'];
        $ds['matkul5'] = $row['matkul5'];
        $ds['sks5'] = $row['jumlah_sks_matkul5'];
        $ds['matkul6'] = $row['matkul6'];
        $ds['sks6'] = $row['jumlah_sks_matkul6'];
        $ds['matkul7'] = $row['matkul7'];
        $ds['sks7'] = $row['jumlah_sks_matkul7'];
        $ds['matkul8'] = $row['matkul8'];
        $ds['sks8'] = $row['jumlah_sks_matkul8'];
        $ds['jumlahsks'] = $row['jumlah_sks'];

        array_push($result['data'], $ds);
        $result['status'] = 'success';
        echo json_encode($result);
        mysqli_close($connect);
    }else{
        $result['status'] = 'Pengguna Tidak Ditemukan';
        echo json_encode($result);
        mysqli_close($connect);
    }


?>