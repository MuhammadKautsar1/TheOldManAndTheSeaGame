-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2024 at 10:52 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tomas`
--

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `id_history` int(11) NOT NULL,
  `level` enum('EASY','MEDIUM','HARD') NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `score` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `marinelife`
--

CREATE TABLE `marinelife` (
  `id_marinelife` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `Classification` varchar(50) NOT NULL,
  `points` int(11) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `marinelife`
--

INSERT INTO `marinelife` (`id_marinelife`, `name`, `Classification`, `points`, `description`) VALUES
(1, 'Ikan Badut', 'Ikan', 10, 'ikan laut kecil yang hidup di perairan hangat terumbu karang, terutama di Samudra Hindia dan Pasifik. Ikan badut memiliki hubungan simbiosis dengan anemon laut, di mana mereka mendapatkan perlindungan dari predator berkat racun anemon yang tidak memengaruhi mereka, sementara anemon mendapat manfaat dari sisa makanan ikan badut. Ikan ini dikenal karena warna tubuhnya yang cerah, dengan corak oranye dan putih, serta kemampuan unik untuk berubah kelamin, biasanya dari jantan menjadi betina saat diperlukan dalam kelompok sosial mereka.'),
(2, 'Ikan Dori', 'Ikan', 20, 'Spesies ikan laut yang hidup di perairan dangkal hingga kedalaman sekitar 200 meter. Ikan ini memiliki tubuh pipih, oval, dan berwarna abu-abu kehijauan dengan ciri khas bintik hitam di kedua sisinya, yang diyakini berfungsi sebagai mekanisme pertahanan terhadap predator. Dori memiliki sirip punggung berduri dan kepala besar dengan mulut yang dapat menonjol saat berburu mangsa, seperti ikan kecil dan invertebrata. Ikan ini terkenal karena dagingnya yang lembut, rendah lemak, serta kaya akan protein dan asam lemak omega-3.'),
(3, 'Ikan Cupang', 'Ikan', 30, 'Jenis ikan air tawar yang berasal dari Asia Tenggara, terutama Thailand, Kamboja, Vietnam, dan Malaysia. Ikan ini sangat populer sebagai ikan hias karena keindahan siripnya yang lebar dan berwarna-warni');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`) VALUES
(1, 'ghazy', 'asdw'),
(2, 'syaban', '123'),
(3, 'yupi', '123');

-- --------------------------------------------------------

--
-- Table structure for table `user_catch`
--

CREATE TABLE `user_catch` (
  `id_catch` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `id_marinelife` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id_history`),
  ADD KEY `id_user` (`id_user`);

--
-- Indexes for table `marinelife`
--
ALTER TABLE `marinelife`
  ADD PRIMARY KEY (`id_marinelife`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- Indexes for table `user_catch`
--
ALTER TABLE `user_catch`
  ADD PRIMARY KEY (`id_catch`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_marinelife` (`id_marinelife`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `history`
--
ALTER TABLE `history`
  MODIFY `id_history` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `marinelife`
--
ALTER TABLE `marinelife`
  MODIFY `id_marinelife` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user_catch`
--
ALTER TABLE `user_catch`
  MODIFY `id_catch` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `history`
--
ALTER TABLE `history`
  ADD CONSTRAINT `history_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `user_catch`
--
ALTER TABLE `user_catch`
  ADD CONSTRAINT `user_catch_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`),
  ADD CONSTRAINT `user_catch_ibfk_2` FOREIGN KEY (`id_marinelife`) REFERENCES `marinelife` (`id_marinelife`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
