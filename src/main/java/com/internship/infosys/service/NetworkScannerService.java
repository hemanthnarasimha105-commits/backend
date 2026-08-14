package com.internship.infosys.service;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.infosys.model.Asset;
import com.internship.infosys.repositary.AssetRepository;

@Service
public class NetworkScannerService {

    @Autowired
    private AssetRepository repository;

    /**
     * Scan a subnet.
     *
     * Example:
     * 192.168.1
     *
     * Scans:
     * 192.168.1.1
     * 192.168.1.2
     * ...
     * 192.168.1.254
     *
     * Multiple Asset records are allowed to have the same IP address.
     */
    public List<Asset> scanNetwork(String subnet) {

        List<Asset> discoveredAssets = new ArrayList<>();

        // Remove trailing dot if user sends:
        // 192.168.1.
        if (subnet != null && subnet.endsWith(".")) {
            subnet = subnet.substring(0, subnet.length() - 1);
        }

        if (subnet == null || subnet.isBlank()) {
            return discoveredAssets;
        }

        for (int i = 1; i <= 254; i++) {

            String ip = subnet + "." + i;

            try {

                InetAddress address = InetAddress.getByName(ip);

                // Check whether host is reachable
                if (!address.isReachable(1000)) {
                    continue;
                }

                // =====================================================
                // IMPORTANT
                // =====================================================
                // Do NOT use findByIpAddress() here.
                //
                // Multiple assets can have the same IP address.
                //
                // Therefore we create a new Asset record for the
                // discovered network device.
                // =====================================================

                Asset asset = new Asset();

                // =====================================================
                // Network Information
                // =====================================================

                asset.setIpAddress(ip);

                String host = address.getHostName();

                if (host == null || host.equals(ip)) {
                    host = "Unknown-" + i;
                }

                asset.setHostname(host);

                // =====================================================
                // Asset Information
                // =====================================================

                asset.setAssetName(host);

                asset.setDescription(
                        "Asset discovered automatically by network scanner"
                );

                asset.setAssetType("Server");

                asset.setDeviceType("Network Device");

                asset.setManufacturer("Unknown");

                asset.setModel("Unknown");

                // =====================================================
                // Assignment
                // =====================================================

                asset.setOwner("System");

                asset.setDepartment("IT");

                asset.setAssignedDepartment("IT");

                asset.setAssignedUser("System");

                asset.setAssignedBy("SentinelCore");

                asset.setAssignmentStatus("Assigned");

                asset.setAssignedDate(LocalDate.now());

                asset.setLocation("Network Discovery");

                // =====================================================
                // Network Details
                // =====================================================

                asset.setMacAddress("Unknown");

                asset.setWifiName("Unknown");

                asset.setGateway("Unknown");

                asset.setSubnetMask("255.255.255.0");

                asset.setDnsServer("Unknown");

                // =====================================================
                // Operating System
                // =====================================================

                asset.setOperatingSystem("Unknown");

                asset.setOsVersion("Unknown");

                asset.setArchitecture("Unknown");

                asset.setProcessor("Unknown");

                asset.setCpuCores(0);

                // =====================================================
                // Live Metrics
                // =====================================================

                asset.setCpuUsage(0);

                asset.setMemoryUsage(0);

                asset.setDiskUsage(0);

                asset.setNetworkUsage(0);

                asset.setGpuUsage(0);

                // =====================================================
                // Security
                // =====================================================

                asset.setStatus("ACTIVE");

                asset.setHealth("Healthy");

                asset.setRiskScore(0);

                asset.setAvailability(99.99);

                asset.setVulnerabilityCount(0);

                asset.setIncidentCount(0);

                asset.setPatchLevel("Unknown");

                // =====================================================
                // Discovery Information
                // =====================================================

                asset.setDiscoveredBy(
                        "SentinelCore Network Scanner"
                );

                asset.setScanStatus("Completed");

                asset.setScanDuration("1 sec");

                asset.setDiscoveryDate(LocalDate.now());

                asset.setDiscoveryTime(LocalTime.now());

                asset.setDiscoveredAt(LocalDateTime.now());

                // =====================================================
                // Audit Information
                // =====================================================

                asset.setCreatedAt(LocalDateTime.now());

                asset.setUpdatedAt(LocalDateTime.now());

                asset.setLastSeen(LocalDateTime.now());

                asset.setLastScan(LocalDateTime.now());

                // =====================================================
                // SAVE
                // =====================================================

                Asset savedAsset = repository.save(asset);

                discoveredAssets.add(savedAsset);

            } catch (Exception e) {

                // Host is unreachable or another network error occurred.
                // Continue scanning the remaining IP addresses.

            }
        }

        return discoveredAssets;
    }
}
