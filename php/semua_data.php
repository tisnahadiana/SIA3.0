<?php 

require_once 'connection.php';

$query = "select * from mahasiswa";

$sql = mysqli_query($connect,$query);

$ray = array();

while ($row = mysqli_fetch_array($sql)){
    array_push($ray, array(
        "id_mhs"=>$row['id'],
        "nama_mhs"=>$row['nama'],
        "kelas_mhs"=>$row['kelas']
    ));
}

echo json_encode($ray);
mysqli_close($connect);




?>