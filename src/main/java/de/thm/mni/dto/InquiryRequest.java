package de.thm.mni.dto;

/**
 * Request für Anfrage an Hangaranbieter (UC FB.3)
 */
public record InquiryRequest(Long parkingId, String message, String aircraftOwnerEmail) {
}
