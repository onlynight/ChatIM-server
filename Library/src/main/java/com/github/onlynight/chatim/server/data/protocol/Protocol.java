// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: protocol_type/protocol_type.proto

package com.github.onlynight.chatim.server.data.protocol;

public final class Protocol {
  private Protocol() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  /**
   * Protobuf enum {@code ProtocolType}
   */
  public enum ProtocolType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>LOGIN = 100;</code>
     */
    LOGIN(0, 100),
    /**
     * <code>TEXT_MESSAGE = 101;</code>
     */
    TEXT_MESSAGE(1, 101),
    /**
     * <code>BROAD_TEXT_MESSAGE = 102;</code>
     */
    BROAD_TEXT_MESSAGE(2, 102),
    /**
     * <code>LOGIN_RESULT = 103;</code>
     */
    LOGIN_RESULT(3, 103),
    /**
     * <code>I_HANDSHAKE = 200;</code>
     */
    I_HANDSHAKE(4, 200),
    /**
     * <code>I_LOGIN = 201;</code>
     */
    I_LOGIN(5, 201),
    /**
     * <code>I_LOGOUT = 202;</code>
     */
    I_LOGOUT(6, 202),
    /**
     * <code>I_TEXT_MESSAGE = 203;</code>
     */
    I_TEXT_MESSAGE(7, 203),
    /**
     * <code>I_BROAD_TEXT_MESSAGE = 204;</code>
     */
    I_BROAD_TEXT_MESSAGE(8, 204),
    ;

    /**
     * <code>LOGIN = 100;</code>
     */
    public static final int LOGIN_VALUE = 100;
    /**
     * <code>TEXT_MESSAGE = 101;</code>
     */
    public static final int TEXT_MESSAGE_VALUE = 101;
    /**
     * <code>BROAD_TEXT_MESSAGE = 102;</code>
     */
    public static final int BROAD_TEXT_MESSAGE_VALUE = 102;
    /**
     * <code>LOGIN_RESULT = 103;</code>
     */
    public static final int LOGIN_RESULT_VALUE = 103;
    /**
     * <code>I_HANDSHAKE = 200;</code>
     */
    public static final int I_HANDSHAKE_VALUE = 200;
    /**
     * <code>I_LOGIN = 201;</code>
     */
    public static final int I_LOGIN_VALUE = 201;
    /**
     * <code>I_LOGOUT = 202;</code>
     */
    public static final int I_LOGOUT_VALUE = 202;
    /**
     * <code>I_TEXT_MESSAGE = 203;</code>
     */
    public static final int I_TEXT_MESSAGE_VALUE = 203;
    /**
     * <code>I_BROAD_TEXT_MESSAGE = 204;</code>
     */
    public static final int I_BROAD_TEXT_MESSAGE_VALUE = 204;


    public final int getNumber() { return value; }

    public static ProtocolType valueOf(int value) {
      switch (value) {
        case 100: return LOGIN;
        case 101: return TEXT_MESSAGE;
        case 102: return BROAD_TEXT_MESSAGE;
        case 103: return LOGIN_RESULT;
        case 200: return I_HANDSHAKE;
        case 201: return I_LOGIN;
        case 202: return I_LOGOUT;
        case 203: return I_TEXT_MESSAGE;
        case 204: return I_BROAD_TEXT_MESSAGE;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ProtocolType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<ProtocolType>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ProtocolType>() {
            public ProtocolType findValueByNumber(int number) {
              return ProtocolType.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.github.onlynight.chatim.server.data.protocol.Protocol.getDescriptor().getEnumTypes().get(0);
    }

    private static final ProtocolType[] VALUES = values();

    public static ProtocolType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private ProtocolType(int index, int value) {
      this.index = index;
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:ProtocolType)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n!protocol_type/protocol_type.proto*\264\001\n\014" +
      "ProtocolType\022\t\n\005LOGIN\020d\022\020\n\014TEXT_MESSAGE\020" +
      "e\022\026\n\022BROAD_TEXT_MESSAGE\020f\022\020\n\014LOGIN_RESUL" +
      "T\020g\022\020\n\013I_HANDSHAKE\020\310\001\022\014\n\007I_LOGIN\020\311\001\022\r\n\010I" +
      "_LOGOUT\020\312\001\022\023\n\016I_TEXT_MESSAGE\020\313\001\022\031\n\024I_BRO" +
      "AD_TEXT_MESSAGE\020\314\001B<\n0com.github.onlynig" +
      "ht.chatim.server.data.protocolB\010Protocol"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
