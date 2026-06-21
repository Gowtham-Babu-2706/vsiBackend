package com.backend.api.service;

import com.backend.api.dto.BookingDto;
import com.backend.api.model.Booking;
import com.backend.api.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingDto createBooking(BookingDto dto) {
        Booking booking = convertToEntity(dto);
        if (booking.getSubmittedAt() == null || booking.getSubmittedAt().trim().isEmpty()) {
            booking.setSubmittedAt(Instant.now().toString());
        }
        Booking saved = bookingRepository.save(booking);
        return convertToDto(saved);
    }

    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private BookingDto convertToDto(Booking b) {
        return new BookingDto(
                b.getId(),
                b.getFullName(),
                b.getEmail(),
                b.getPhone(),
                b.getCity(),
                b.getAge(),
                b.getParticipants(),
                b.getNotes(),
                b.getEventId(),
                b.getEventName(),
                b.getTransactionId(),
                b.getPaymentStatus(),
                b.getSubmittedAt()
        );
    }

    private Booking convertToEntity(BookingDto dto) {
        return Booking.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .city(dto.getCity())
                .age(dto.getAge())
                .participants(dto.getParticipants())
                .notes(dto.getNotes())
                .eventId(dto.getEventId())
                .eventName(dto.getEventName())
                .transactionId(dto.getTransactionId())
                .paymentStatus(dto.getPaymentStatus())
                .submittedAt(dto.getSubmittedAt())
                .build();
    }
}
