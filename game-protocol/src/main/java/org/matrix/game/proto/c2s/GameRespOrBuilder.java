// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: test.proto

// Protobuf Java Version: 3.25.1
package org.matrix.game.proto.c2s;

public interface GameRespOrBuilder extends
    // @@protoc_insertion_point(interface_extends:GameResp)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 code = 1;</code>
   * @return The code.
   */
  int getCode();

  /**
   * <pre>
   * 登录
   * </pre>
   *
   * <code>.EnterGameRt enterGameRt = 1001;</code>
   * @return Whether the enterGameRt field is set.
   */
  boolean hasEnterGameRt();
  /**
   * <pre>
   * 登录
   * </pre>
   *
   * <code>.EnterGameRt enterGameRt = 1001;</code>
   * @return The enterGameRt.
   */
  org.matrix.game.proto.c2s.EnterGameRt getEnterGameRt();
  /**
   * <pre>
   * 登录
   * </pre>
   *
   * <code>.EnterGameRt enterGameRt = 1001;</code>
   */
  org.matrix.game.proto.c2s.EnterGameRtOrBuilder getEnterGameRtOrBuilder();

  /**
   * <pre>
   * 测试
   * </pre>
   *
   * <code>.StringMsgRt stringMsgRt = 2001;</code>
   * @return Whether the stringMsgRt field is set.
   */
  boolean hasStringMsgRt();
  /**
   * <pre>
   * 测试
   * </pre>
   *
   * <code>.StringMsgRt stringMsgRt = 2001;</code>
   * @return The stringMsgRt.
   */
  org.matrix.game.proto.c2s.StringMsgRt getStringMsgRt();
  /**
   * <pre>
   * 测试
   * </pre>
   *
   * <code>.StringMsgRt stringMsgRt = 2001;</code>
   */
  org.matrix.game.proto.c2s.StringMsgRtOrBuilder getStringMsgRtOrBuilder();

  /**
   * <code>.NumberMsgRt numberMsgRt = 2002;</code>
   * @return Whether the numberMsgRt field is set.
   */
  boolean hasNumberMsgRt();
  /**
   * <code>.NumberMsgRt numberMsgRt = 2002;</code>
   * @return The numberMsgRt.
   */
  org.matrix.game.proto.c2s.NumberMsgRt getNumberMsgRt();
  /**
   * <code>.NumberMsgRt numberMsgRt = 2002;</code>
   */
  org.matrix.game.proto.c2s.NumberMsgRtOrBuilder getNumberMsgRtOrBuilder();

  org.matrix.game.proto.c2s.GameResp.PayloadCase getPayloadCase();
}
