-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 21, 2018 at 05:48 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mdp`
--
CREATE DATABASE IF NOT EXISTS `mdp` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `mdp`;

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `Id_Booking` varchar(6) NOT NULL,
  `Id_User` varchar(6) NOT NULL,
  `Tanggal_Booking` date NOT NULL,
  `Status_Booking` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `dtour`
--

CREATE TABLE `dtour` (
  `Id_Tour` varchar(6) NOT NULL,
  `Id_User` varchar(6) NOT NULL,
  `Jumlah_Orang` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `htour`
--

CREATE TABLE `htour` (
  `Id_Tour` varchar(6) NOT NULL,
  `Nama_Tour` varchar(50) NOT NULL,
  `Tanggal_Pergi` date NOT NULL,
  `Tanggal_Pulang` date NOT NULL,
  `Harga` varchar(10) NOT NULL,
  `Id_Tour_Leader` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `Id_Message` varchar(6) NOT NULL,
  `Id_Pengirim` varchar(6) NOT NULL,
  `Id_Penerima` varchar(6) NOT NULL,
  `Pesan` varchar(500) NOT NULL,
  `Tanggal_Kirim` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `Id_Payment` varchar(6) NOT NULL,
  `Id_Tour_Leader` varchar(6) NOT NULL,
  `Id_User` varchar(6) NOT NULL,
  `Jumlah` int(11) NOT NULL,
  `Status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `posting`
--

CREATE TABLE `posting` (
  `Id_Posting` varchar(6) NOT NULL,
  `Judul` varchar(100) NOT NULL,
  `Isi` varchar(500) NOT NULL,
  `Tanggal_Posting` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tempat_wisata`
--

CREATE TABLE `tempat_wisata` (
  `Nama_Tempat` varchar(50) NOT NULL,
  `Alamat` varchar(50) NOT NULL,
  `Telpon` varchar(20) NOT NULL,
  `Gambar` varchar(50) NOT NULL,
  `Deksripsi` varchar(500) NOT NULL,
  `Rating` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Id_User` varchar(6) NOT NULL,
  `Nama_User` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Gender` varchar(1) NOT NULL,
  `Tanggal_Lahir` date NOT NULL,
  `Role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`Id_Booking`);

--
-- Indexes for table `htour`
--
ALTER TABLE `htour`
  ADD PRIMARY KEY (`Id_Tour`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`Id_Message`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`Id_Payment`);

--
-- Indexes for table `posting`
--
ALTER TABLE `posting`
  ADD PRIMARY KEY (`Id_Posting`);

--
-- Indexes for table `tempat_wisata`
--
ALTER TABLE `tempat_wisata`
  ADD PRIMARY KEY (`Nama_Tempat`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Id_User`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
