<?php
$conn=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");
    
	   $nim=$_POST['nim'];
	   $nama=$_POST['nama'];
       $prodi=$_POST['prodi'];
       $semester=$_POST['semester'];	   
	   $img=$_POST['upload'];
       $filename="IMG".rand().".jpg";

	   file_put_contents("droomp.tech/khs/".$filename,base64_decode($img));

			$qry="INSERT INTO khs (`nim`, `nama`, `prodi`, `semester`, `khs_url`)
			      VALUES ('$nim', '$nama', '$prodi', '$semester', '$filename')";

			$res=mysqli_query($conn,$qry);
			
			if($res==true)
			 echo "File Uploaded Successfully";
			else
			 echo "Could not upload File";
?>
