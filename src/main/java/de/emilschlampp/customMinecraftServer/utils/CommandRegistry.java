package de.emilschlampp.customMinecraftServer.utils;

import de.emilschlampp.customMinecraftServer.command.*;
import de.emilschlampp.customMinecraftServer.net.ServerConnectionThread;
import de.emilschlampp.customMinecraftServer.net.data.Node;
import de.emilschlampp.customMinecraftServer.packets.play.OutDeclareCommandsPackets;

import java.util.*;

import static de.emilschlampp.customMinecraftServer.net.NetUtils.createVarIntByteArray;
import static de.emilschlampp.customMinecraftServer.utils.StringUtils.removeFirstElement;

public class CommandRegistry {
    private static final Map<String, Command> commandMap = new HashMap<>();
    private static boolean init = false;

    public static void init() {
        if(init) { return; } init = true;

        register(new GameStateCommand());
        register(new GameModeCommand());
        register(new ChangeBlockStateCommand());
        register(new TestSpawnCommand());
        register(new TimeSpamCommand());
        register(new ParticleActionCommand());
        register(new InvisibleCommand());
    }



    public static void register(Command command) {
        commandMap.put(command.getName(), command);
    }

    public static void exec(ServerConnectionThread connectionThread, String[] args) {
        String commandName = "";
        if(args.length == 0) {
            commandName = "";
        } else {
            commandName = args[0];
        }

        if(commandMap.containsKey(args[0])) {
            commandMap.get(commandName).run(connectionThread, removeFirstElement(args));
        }
    }

    public static List<String> tab(ServerConnectionThread connectionThread, String[] args) {
        if(args.length == 0) {
            return new ArrayList<>(commandMap.keySet());
        }
        if(commandMap.containsKey(args[0])) {
            List<String> s = commandMap.get(args[0]).tab(connectionThread, removeFirstElement(args));
            if(s == null) {
                s = new ArrayList<>();
                for (PacketPlayer packetPlayer : PacketPlayer.getOnline(connectionThread)) {
                    s.add(packetPlayer.name);
                }
            }
            return s;
        }
        return new ArrayList<>();
    }

    public static OutDeclareCommandsPackets getDeclarations() {
        List<Node> nodes = new ArrayList<>();

        List<Integer> childList = new ArrayList<>();
        for (Command value : commandMap.values()) {
            childList.add(nodes.size());
            nodes.add(
                    new Node().setNodeType(Node.NodeType.LITERAL.id).recalcFlags().setName(value.getName()).setChildren(new int[] {nodes.size()+1}).setExecutable(true).setRedirectNode(0).recalcFlags()
            );
            nodes.add(
                    new Node().setNodeType(Node.NodeType.ARGUMENT.id).setName("args").setParser("brigadier:string").setProperties(createVarIntByteArray(2)).setSuggestionType("minecraft:ask_server").setHasSuggestionsType(true).recalcFlags()
            );
        }

        int[] children = new int[childList.size()];
        for(int i = 0; i<childList.size(); i++) {
            children[i] = childList.get(i);
        }

        nodes.add(
                new Node().setNodeType(Node.NodeType.ROOT.id).recalcFlags().setChildren(children).convertFlagsToValues()
        );

        return new OutDeclareCommandsPackets(nodes.size()-1, nodes.toArray(new Node[0]));
    }
}
