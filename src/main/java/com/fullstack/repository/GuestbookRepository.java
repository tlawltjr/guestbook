package com.fullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.entity.Guestbook;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {

}
