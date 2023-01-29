-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 29, 2023 at 12:21 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cashier`
--

-- --------------------------------------------------------

--
-- Table structure for table `level`
--

CREATE TABLE `level` (
  `id_level` int(11) NOT NULL,
  `nama_level` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `level`
--

INSERT INTO `level` (`id_level`, `nama_level`) VALUES
(1, 'administrator'),
(2, 'kasir'),
(3, 'owner');

-- --------------------------------------------------------

--
-- Table structure for table `masakan`
--

CREATE TABLE `masakan` (
  `id_masakan` int(11) NOT NULL,
  `nama_masakan` varchar(64) NOT NULL,
  `harga` int(11) NOT NULL,
  `stok` int(11) NOT NULL,
  `deleted` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `masakan`
--

INSERT INTO `masakan` (`id_masakan`, `nama_masakan`, `harga`, `stok`, `deleted`) VALUES
(1, 'Pizza', 4000, 15, 0),
(2, 'Bakso', 5000, 15, 0),
(3, 'cireng', 4000, 74, 1),
(4, 'Bakso 3', 5000, 30, 1),
(5, 'Burger', 60000, 25, 0),
(6, 'Burger', 60000, 53, 1);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `id_order` int(11) NOT NULL,
  `id_transaksi` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `no_meja` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `id_masakan` int(11) NOT NULL,
  `jumlah_masakan` int(11) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `keterangan` text NOT NULL,
  `status_order` enum('Lunas','Belum Dibayar') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`id_order`, `id_transaksi`, `id_user`, `no_meja`, `tanggal`, `id_masakan`, `jumlah_masakan`, `total_harga`, `keterangan`, `status_order`) VALUES
(1, 9, 1, 4, '2023-01-18', 1, 5, 20000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(2, 10, 1, 3, '2023-01-18', 2, 5, 25000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(3, 11, 1, 4, '2023-01-18', 1, 5, 20000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(4, 11, 1, 4, '2023-01-18', 1, 5, 20000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(5, 12, 1, 5, '2023-01-18', 1, 10, 40000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(6, 12, 1, 5, '2023-01-18', 1, 10, 40000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(7, 12, 1, 5, '2023-01-18', 2, 5, 25000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(8, 13, 1, 5, '2023-01-19', 1, 20, 80000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(9, 13, 1, 5, '2023-01-19', 2, 37, 185000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(10, 14, 1, 6, '2023-01-19', 5, 10, 600000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(11, 14, 1, 6, '2023-01-19', 5, 10, 600000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(12, 14, 1, 6, '2023-01-19', 5, 10, 600000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(13, 14, 1, 6, '2023-01-19', 5, 10, 600000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(14, 15, 1, 1, '2023-01-29', 1, 5, 20000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(15, 15, 1, 1, '2023-01-29', 5, 12, 720000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(16, 16, 1, 2, '2023-01-29', 5, 5, 300000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(17, 17, 1, 1, '2023-01-29', 5, 5, 300000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(18, 18, 1, 1, '2023-01-29', 2, 5, 25000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(19, 19, 1, 1, '2023-01-29', 1, 1, 4000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(20, 20, 1, 1, '2023-01-29', 1, 1, 4000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(21, 21, 1, 1, '2023-01-29', 1, 1, 4000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(22, 21, 1, 1, '2023-01-29', 2, 1, 5000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(23, 22, 1, 1, '2023-01-29', 1, 1, 4000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(24, 22, 1, 1, '2023-01-29', 5, 1, 60000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(25, 22, 1, 1, '2023-01-29', 2, 1, 5000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(26, 23, 1, 1, '2023-01-29', 5, 1, 60000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(27, 23, 1, 1, '2023-01-29', 2, 1, 5000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(28, 24, 1, 1, '2023-01-29', 1, 1, 4000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(29, 24, 1, 1, '2023-01-29', 2, 1, 5000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(30, 24, 1, 1, '2023-01-29', 5, 1, 60000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(31, 25, 1, 1, '2023-01-29', 1, 1, 4000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(32, 25, 1, 1, '2023-01-29', 2, 1, 5000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(33, 25, 1, 1, '2023-01-29', 5, 1, 60000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(34, 26, 1, 1, '2023-01-29', 2, 5, 25000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(35, 26, 1, 1, '2023-01-29', 1, 5, 20000, 'Pesanan dilayani oleh kasir', 'Lunas'),
(36, 26, 1, 1, '2023-01-29', 5, 7, 420000, 'Pesanan dilayani oleh kasir', 'Lunas');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `total_bayar` int(11) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `kembalian` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_user`, `tanggal`, `total_bayar`, `total_harga`, `kembalian`) VALUES
(9, 1, '2023-01-18', 30000, 20000, 10000),
(10, 1, '2023-01-18', 50000, 25000, 25000),
(11, 1, '2023-01-18', 60000, 52000, 8000),
(12, 1, '2023-01-18', 400000, 365000, 35000),
(13, 1, '2023-01-19', 400000, 345000, 55000),
(14, 1, '2023-01-19', 3000000, 2700000, 300000),
(15, 1, '2023-01-29', 4545455, 740000, 3805455),
(16, 1, '2023-01-29', 400000, 300000, 100000),
(17, 1, '2023-01-29', 400000, 300000, 100000),
(18, 1, '2023-01-29', 50000, 25000, 25000),
(19, 1, '2023-01-29', 5000, 4000, 1000),
(20, 1, '2023-01-29', 5000, 4000, 1000),
(21, 1, '2023-01-29', 10000, 9000, 1000),
(22, 1, '2023-01-29', 70000, 69000, 1000),
(23, 1, '2023-01-29', 70000, 69000, 1000),
(24, 1, '2023-01-29', 70000, 69000, 1000),
(25, 1, '2023-01-29', 70000, 69000, 1000),
(26, 1, '2023-01-29', 500000, 465000, 35000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `id_level` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `name`, `username`, `password`, `id_level`) VALUES
(1, 'Admin', 'admin', 'admin', 1),
(3, 'owner', 'owner', 'owner', 3),
(4, 'kasir', 'kasir', 'kasir', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `level`
--
ALTER TABLE `level`
  ADD PRIMARY KEY (`id_level`);

--
-- Indexes for table `masakan`
--
ALTER TABLE `masakan`
  ADD PRIMARY KEY (`id_masakan`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id_order`),
  ADD KEY `fk_order_id_masakan` (`id_masakan`),
  ADD KEY `fk_order_id_user` (`id_user`) USING BTREE,
  ADD KEY `fk_order_id_transaksi` (`id_transaksi`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `fk_transaksi_id_user` (`id_user`) USING BTREE;

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD KEY `fk_user_id_level` (`id_level`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `level`
--
ALTER TABLE `level`
  MODIFY `id_level` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `masakan`
--
ALTER TABLE `masakan`
  MODIFY `id_masakan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `id_order` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_order_id_masakan` FOREIGN KEY (`id_masakan`) REFERENCES `masakan` (`id_masakan`),
  ADD CONSTRAINT `fk_order_id_transaksi` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`),
  ADD CONSTRAINT `fk_order_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `fk_transaksi_id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_id_level` FOREIGN KEY (`id_level`) REFERENCES `level` (`id_level`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
