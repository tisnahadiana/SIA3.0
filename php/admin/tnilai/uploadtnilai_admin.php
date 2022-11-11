<?php
$conn=mysqli_connect("localhost","u606784881_hadiana","@Hasan90256801","u606784881_sia");
    
	   $nim=$_POST['nim'];
	   $nama=$_POST['nama'];
       $prodi=$_POST['prodi'];
       $semester=$_POST['semester'];	   
	   $img=$_POST['upload'];
       $filename="IMG".rand().".jpg";

	   file_put_contents("images/".$filename,base64_decode($img));

			$qry="INSERT INTO tnilai (`nim`, `nama`, `prodi`, `semester`, `tnilai_url`)
			      VALUES ('$nim', '$nama', '$prodi', '$semester', 'https://droomp.tech/admin/tnilai/images/$filename')";

			$res=mysqli_query($conn,$qry);
			
			if($res==true)
			 echo "File Uploaded Successfully";
			else
			 echo "Could not upload File";
?>
