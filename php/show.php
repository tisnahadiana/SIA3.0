<?php

   

    if($_SERVER["REQUEST_METHOD"]=="POST")
    
    {

        include 'connection.php';

        showmahasiswa();
    }

    function showmahasiswa(){

        global $connect;
        $nim = $_GET["nim"];
        $nama = $_GET["nama"];

        $query = "SELECT * FROM mahasiswa WHERE nim = '$nim' AND nama = '$nama'";
        $result = mysqli_query($connect, $query); 
        $number_of_rows = mysqli_num_rows($result);

        if ($number_of_rows > 0){

            while ($row = mysqli_fetch_assoc($result)){

                $temp_array[]= $row;
                get_resources(json_encode(array("nama"=>$temp_array)));
                get_resources(json_encode(array("nim"=>$temp_array)));
                echo $result;

            }

        }

        mysqli_close($connect);



    }
 ?>