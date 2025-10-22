package com.route_management_system.RMS.model.enums;

public enum VehicleType {
        BIKE(50, 0.5),
        VAN(1000, 8),
        TRUCK(5000, 40);

        private final double maxWeightKg;
        private final double maxVolumeM3;


        VehicleType(double maxWeightKg, double maxVolumeM3) {
            this.maxWeightKg = maxWeightKg;
            this.maxVolumeM3 = maxVolumeM3;
        }

        public double getMaxWeightKg() {
            return maxWeightKg;
        }

        public double getMaxVolumeM3() {
            return maxVolumeM3;
        }


}
