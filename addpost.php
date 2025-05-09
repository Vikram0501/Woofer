<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
$username = "s2798790";
$password = "s2798790";
$database = "d2798790";
$conn = new mysqli("127.0.0.1", $username, $password, $database);
$id = $_GET['id'] ?? '';
$input = $_GET['input'] ?? '';
$code = $_GET['code'] ?? '';

if ($conn->connect_error){
    die("Connection Error: " . $conn->connect_error);
}

if (empty($input) || empty($code) || empty($id)){
    die("ERR: MISSING FIELDS");
}

?>