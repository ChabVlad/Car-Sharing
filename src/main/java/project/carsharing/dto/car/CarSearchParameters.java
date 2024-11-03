package project.carsharing.dto.car;

public record CarSearchParameters(
        String[] model,
        String[] brand,
        String[] type,
        String[] dailyFee
) {
}
