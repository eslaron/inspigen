package org.sobiech.inspigen.app.domain.participant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository  extends JpaRepository<Participant,Long> {

}
