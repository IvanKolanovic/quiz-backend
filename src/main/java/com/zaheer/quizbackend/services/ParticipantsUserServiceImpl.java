package com.zaheer.quizbackend.services;

import com.zaheer.quizbackend.services.interfaces.ParticipantsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantsUserServiceImpl extends BaseService implements ParticipantsService {}
