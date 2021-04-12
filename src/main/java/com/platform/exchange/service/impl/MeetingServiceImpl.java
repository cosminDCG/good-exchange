package com.platform.exchange.service.impl;

import com.platform.exchange.exception.ErrorMessage;
import com.platform.exchange.exception.meeting.MeetingNotFoundException;
import com.platform.exchange.exception.user.UserNotFoundException;
import com.platform.exchange.model.Meeting;
import com.platform.exchange.model.User;
import com.platform.exchange.repository.MeetingRepository;
import com.platform.exchange.repository.ProductRepository;
import com.platform.exchange.repository.UserRepository;
import com.platform.exchange.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Meeting saveMeeting(Meeting meeting) {
        meeting.setId(UUID.randomUUID());
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void deleteMeeting(String uuid) {
        Meeting meeting = meetingRepository.findById(UUID.fromString(uuid))
                                           .orElseThrow(() -> new MeetingNotFoundException(ErrorMessage.MEETING_NOT_FOUND));
        meetingRepository.delete(meeting);
    }

    @Override
    @Transactional
    public List<Meeting> getUpcomingMeetingsByUser(String uuid) {
        User user = userRepository.findById(UUID.fromString(uuid))
                                  .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        return meetingRepository.findAllBySellerOrBuyerAndDateGreaterThan(user, user, Date.from(Instant.now()));
    }

    @Override
    @Transactional
    public List<Meeting> getPreviousMeetingsByUser(String uuid) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        return meetingRepository.findAllBySellerOrBuyerAndDateLessThan(user, user, Date.from(Instant.now()));
    }

    @Override
    @Transactional
    public List<Meeting> getAllMeetingsByUser(String uuid) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        return meetingRepository.findAllBySellerOrBuyer(user, user);
    }

    @Override
    @Transactional
    public Meeting respondToMeeting(String uuid, boolean response) {
        Meeting meeting = meetingRepository.findById(UUID.fromString(uuid))
                                           .orElseThrow(() -> new MeetingNotFoundException(ErrorMessage.MEETING_NOT_FOUND));
        meeting.setApproved(response);
        meeting.getProduct().setAvailable(!response);

        productRepository.save(meeting.getProduct());
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void failureMeeting(String uuid) {
        Meeting meeting = meetingRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new MeetingNotFoundException(ErrorMessage.MEETING_NOT_FOUND));
        meeting.getProduct().setAvailable(true);

        productRepository.save(meeting.getProduct());
        meetingRepository.delete(meeting);
    }

    @Override
    @Transactional
    public Meeting updateMeeting(Meeting meeting) {
        meetingRepository.findById(meeting.getId())
                         .orElseThrow(() -> new MeetingNotFoundException(ErrorMessage.MEETING_NOT_FOUND));
        return meetingRepository.save(meeting);
    }
}
