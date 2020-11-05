package com.fisglobal.waho.services;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fisglobal.waho.controller.ShiftScheduleController;

public class EmitterService {
	static Logger log = LoggerFactory.getLogger(EmitterService.class);
	
	public HashMap<Long,SseEmitter> emittersMap = new HashMap<>();

	public HashMap<Long, SseEmitter> getEmittersMap() {
		return emittersMap;
	}

	public void setEmittersMap(HashMap<Long, SseEmitter> emittersMap) {
		this.emittersMap = emittersMap;
	}
	
	public void addEmitter(Long idKey, SseEmitter emitter) {
		emittersMap.put(idKey, emitter);
		log.debug("Added emitter id: "+idKey+" | emitter count: "+emittersMap.size());
	}
	
	public void removeEmitter(Long idKey) {
		emittersMap.remove(idKey);
		log.debug("Removed emitter id: "+idKey+" | emitter count: "+emittersMap.size());
	}
}
